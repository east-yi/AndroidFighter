package com.ycc.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ycc.bodyguard.R;
import com.ycc.db.AddressDao;
import com.ycc.util.ReleaseCorrelation;

/**
 * 监听电话状态，显示浮动Toast
 * Created by Administrator on 16-11-24.
 */
public class ShowFloatService extends Service{
    //获取窗体对象
    private WindowManager mWM;
    private View viewToast;

    private TelephonyManager tm;
    private InnerPhoneStateListener mListtener;
    private TextView textView;
    public String content;


    @Override
    public void onCreate() {
        tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //监听
        mListtener=new InnerPhoneStateListener();
        tm.listen(mListtener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    /**
     * 来电监听类
     */
    class  InnerPhoneStateListener extends PhoneStateListener{

        @Override
        public void onCallStateChanged(int state,  String incomingNumber) {
            switch (state) {
                //空闲状态，没有任何活动
                case TelephonyManager.CALL_STATE_IDLE:
                    if(mWM!=null&&viewToast!=null){
                        mWM.removeView(viewToast);
                    }
                    break;
                //摘机状态，至少拨打或通话其中一种
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                //响铃状态
                case TelephonyManager.CALL_STATE_RINGING:
                    //窗体对象
                    mWM = (WindowManager) ShowFloatService.this.getSystemService(Context.WINDOW_SERVICE);
                    //显示效果：自定义布局
                   viewToast = View.inflate(ShowFloatService.this, R.layout.toast_view, null);

                    //查询数据库，设置归属地
                    String string = AddressDao.queryPhone(incomingNumber);
                    ReleaseCorrelation.showT(incomingNumber+"=======");
                    //弹出自定义Toast
                    ReleaseCorrelation.customToast(ShowFloatService.this,mWM,viewToast,string);
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
