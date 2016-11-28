package com.ycc.mylibrary.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * 自定义Toast类
 * Created by Administrator on 16-11-28.
 */
public class CustomToast {
    //挂载的View
    private static View view=null;
    //窗体管理对象
    private static WindowManager mWM=null;
    /**
     * 显示自定义的Toast
     * @param context 上下文
     * @param layout  自定义的布局
     */
    public static void showCustomToast(Context context,int layout){
        //1.如果已经显示，就不显示了
        if(mWM!=null&&view!=null){
            return;
        }
        //2.获得窗体管理对象
        mWM=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        //3.布局加载为View
        view = LayoutInflater.from(context).inflate(layout, null);
        //4.定义view的属性
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;//高
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//宽
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE//不能获取焦点
                |WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE//不能触摸(默认是可以触摸的)
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;//屏幕开启才能看到
        //格式
        mParams.format = PixelFormat.TRANSLUCENT;
        //显示级别:和Toast级别一致
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        //显示位置：右上角
        mParams.gravity = Gravity.RIGHT + Gravity.TOP;
        //5.挂载到窗体
        mWM.addView(view,mParams);
    }
    /**
     * 移除窗体上挂载的View
     */
    public static void removeCustomToast(){
        if(mWM!=null&&view!=null){
            mWM.removeView(view);
            //重置为null,反正两次取消报错
            mWM=null;
            view=null;
        }
    }
}

