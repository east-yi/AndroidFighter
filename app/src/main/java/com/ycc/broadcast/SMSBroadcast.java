package com.ycc.broadcast;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.telephony.SmsMessage;

import com.ycc.MyApplication;
import com.ycc.bodyguard.R;
import com.ycc.constant.SpKeyPool;
import com.ycc.util.ReleaseCorrelation;
import com.ycc.util.SpUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 监听短信广播
 * 每个方法都可以用：只是需要跳转系统界面的逻辑要显试的context才能调用有效，否则报错（比如启动设备管理器、锁屏等）
 * Created by Administrator on 16-11-22.
 */
public class SMSBroadcast extends BroadcastReceiver {
    private List<String> list;
    public static String OPPOSITE_PHONE = "opposite_phone";
    public static String phone;
    private DevicePolicyManager mDPM;
    private ComponentName mComponentName;
    private String smsContent;

    @Override
    public void onReceive(Context context, Intent intent) {

        //一切操作都建立在开启防盗保护基础之上
        if (SpUtils.getBooleanS(context, SpKeyPool.VTD_WIZARD, false)) {
            try {
                //读取文件中的文本信息（指定短信内容及逻辑）
                list = readTxt(context);
                //获取短信内容
                getSmsInfo(context, intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件
     *
     * @param context
     * @return 每一行数据的集合
     */
    private List<String> readTxt(Context context) throws Exception {
        list = new ArrayList<>();
        InputStream inputStream = context.getResources().openRawResource(R.raw.receive_sms_sxplain);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = reader.readLine()) != null) {
            line.trim();
            list.add(line);
        }
        return list;
    }

    /**
     * 如果接收短信中有指定的内容就播放报警音乐
     *
     * @param context
     */
    private void getSmsInfo(Context context, Intent intent) {
        //接收短信的内容
        Object[] obj = (Object[]) intent.getExtras().get("pdus");
        for (Object object : obj) {
            //获取短信对象
            SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
            //获取短信信息
            phone = message.getOriginatingAddress();//对方的号码
            smsContent = message.getMessageBody();//内容
            //过滤
            filtrationSms(context);
        }
    }

    /**
     * 短信过滤
     *
     * @param context
     */
    public void filtrationSms(Context context) {
        if (smsContent.contains(list.get(1))) {
            //播放音乐
            MediaPlayer media = MediaPlayer.create(context, R.raw.dongxiaojie);
            media.setLooping(true);
            media.start();
        } else if (smsContent.contains(list.get(3))) {
            //启动服务，定位，回发位置短信
            MyApplication.intent.putExtra(SMSBroadcast.OPPOSITE_PHONE, phone);
            MyApplication.app.startService(MyApplication.intent);
        } else if (smsContent.contains(list.get(5))) {
            //删除数据，需要激活设备管理器
            remoteDelete(context);
        } else if (smsContent.contains(list.get(7))) {
            //远程锁屏
            remoteLockScreen(context);
        } else if (smsContent.contains(list.get(9))) {
            //远程卸载
            remoteUnload(context);
        }
    }


    /**
     * 远程删除数据【慎用】
     */
    private void remoteDelete(Context context) {
        if (startDevice(context)) {
            //删除数据
            mDPM.wipeData(0);//手机数据
            //慎用、慎用、慎用
//            mDPM.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//SD卡数据
        }

    }

    /**
     * 远程锁屏
     */
    private void remoteLockScreen(Context context) {
        if (startDevice(context)) {
            //锁屏
            mDPM.lockNow();
            //密码是当天日期
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            date = date.replace("-", "");
            mDPM.resetPassword(date, 0);
        }
    }

    /**
     * 远程卸载本软件（跳转卸载界面）
     *
     * @param context
     */
    private void remoteUnload(Context context) {
        if (startDevice(context)) {
            ReleaseCorrelation.showT("该应用有超级权限，取消激活才能卸载");
            //隐式启动
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        }
    }


    /**
     * 开启设备管理器的Activity
     */
    public boolean startDevice(Context context) {
        //设备管理器服务
        mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        //需要激活的组件名称
        mComponentName = new ComponentName(context, DeviceAdmin.class);
        //先判断是否激活
        if (mDPM.isAdminActive(mComponentName)) {
            return true;
        } else {
            ReleaseCorrelation.showT("请激活本软件的权限");
            //隐式启动
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "保卫萝卜管理者");
            MyApplication.app.startActivity(intent);
            return false;
        }
    }
}
