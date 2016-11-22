package com.ycc.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.ycc.constant.SpKeyPool;
import com.ycc.util.SpUtils;

/**
 * 监听开机广播
 * Created by Administrator on 16-11-22.
 */
public class StartingUpBroadcast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
            sendSms(context);
    }

    /**
     * sim卡变更时，给安全号码发送短信
     */
    public void sendSms(Context context){
        //判断是否和本地存储的sim卡一致，不一致给安全号码发送短信
        String sim = SpUtils.getString(context, SpKeyPool.IS_DBSIM, "");
        //本次开机的sim卡号
        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String thisSim = tm.getSimSerialNumber();
        //存在电话卡
        if(!sim.equals(thisSim)){
            //发送短信给安全号码
            SmsManager sm = SmsManager.getDefault();
            String phone = SpUtils.getString(context, SpKeyPool.SAFETY_PHONE, "");
            //记得加权限
            sm.sendTextMessage(phone,null,"警惕，您的手机更换了sim卡！",null,null);
        }
    }

}
