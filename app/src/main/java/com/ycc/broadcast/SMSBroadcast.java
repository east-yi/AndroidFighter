package com.ycc.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import com.ycc.bodyguard.R;
import com.ycc.constant.SpKeyPool;
import com.ycc.util.SpUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 监听短信广播
 * Created by Administrator on 16-11-22.
 */
public class SMSBroadcast extends BroadcastReceiver{
    private List<String> list;

    @Override
    public void onReceive(Context context, Intent intent) {

        //一切操作都建立在开启防盗保护基础之上
        if(SpUtils.getBooleanS(context, SpKeyPool.VTD_WIZARD,false)) {
            try {
                //读取文件中的文本信息（指定短信内容及逻辑）
                list = readTxt(context);
                //播放报警音乐
                playMusic(context,intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件
     * @param context
     * @return 每一行数据的集合
     */
    private List<String> readTxt(Context context) throws Exception {
        list=new ArrayList<>();
        InputStream inputStream =context.getResources().openRawResource(R.raw.receive_sms_sxplain);
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
     * @param context
     */
    private void playMusic(Context context,Intent intent) {
        //我们指定的内容
        String playMusic=list.get(1);
        //接收短信的内容
        Object[] obj = (Object[]) intent.getExtras().get("pdus");
        for (Object object:obj) {
            //获取短信对象
            SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
            //获取短信信息
            String phone = message.getOriginatingAddress();//对方的号码
            String content = message.getMessageBody();//内容
            if(content.contains(playMusic)){
                //播放音乐
                MediaPlayer media = MediaPlayer.create(context,R.raw.dongxiaojie );
                media.setLooping(true);
                media.start();
            }
        }


    }
}
