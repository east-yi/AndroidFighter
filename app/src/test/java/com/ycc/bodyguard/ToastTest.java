package com.ycc.bodyguard;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.ycc.util.ActivityManage;

/**
 * Created by Administrator on 16-11-28.
 */
public class ToastTest extends ActivityManage{

    WindowManager mWM=(WindowManager)getSystemService(Context.WINDOW_SERVICE);
    /**
     * 显示自定义的Toast
     * @param context 上下文
     *  @param mWM 窗体管理对象
     * @param layout  自定义的布局
     */
    public void showCustomToast(Context context,WindowManager mWM ,int layout){
        //布局加载为View
        View view = LayoutInflater.from(context).inflate(layout, null);
        //定义view的属性
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;//高
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//宽
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE//不能获取焦点
//                |WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE//不能触摸(默认是可以触摸的)
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;//屏幕开启才能看到
        //格式
        mParams.format = PixelFormat.TRANSLUCENT;
        //显示级别:和Toast级别一致
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        //显示位置：右上角
        mParams.gravity = Gravity.RIGHT + Gravity.TOP;
        //挂载到窗体
        mWM.addView(view,mParams);
    }

    /**
     * 移除窗体上挂载的View
     * @param context 上下文
     * @param mWM 窗体管理对象
     * @param view  挂载的View
     */
    public void removeCustomToast(Context context,WindowManager mWM ,View view){
        if(mWM!=null&&view!=null){
             mWM.removeView(view);
        }
    }



}
