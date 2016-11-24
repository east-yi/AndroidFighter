package com.ycc.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.ycc.util.ReleaseCorrelation;

/**
 * 监听电话状态，显示浮动Toast
 * Created by Administrator on 16-11-24.
 */
public class ShowFloatService extends Service{


    private TelephonyManager tm;
    private InnerPhoneStateListener mListtener;

    @Override
    public void onCreate() {
        tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //监听
        mListtener=new InnerPhoneStateListener();
        tm.listen(mListtener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    /**
     * 监听类
     */
    class  InnerPhoneStateListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                //空闲状态，没有任何活动
                case TelephonyManager.CALL_STATE_IDLE:
                    //TODO
                    ReleaseCorrelation.getLog("log","log空闲-------");
                    break;
                //摘机状态，至少拨打或通话其中一种
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    ReleaseCorrelation.getLog("log","log拨打-------");
                    break;
                //响铃状态
                case TelephonyManager.CALL_STATE_RINGING:
                    ReleaseCorrelation.getLog("log","log响铃-------");
                    break;
            }
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        //即使服务停止了系统也还会保留监听,所以得手动取消电话监听
        if(tm!=null&&mListtener!=null) {
            tm.listen(mListtener, PhoneStateListener.LISTEN_NONE);
        }
        super.onDestroy();
    }
}
