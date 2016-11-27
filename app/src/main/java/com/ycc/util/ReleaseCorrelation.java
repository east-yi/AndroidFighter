package com.ycc.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ycc.MyApplication;
import com.ycc.bodyguard.R;
import com.ycc.constant.SpKeyPool;

/**
 * Created by Administrator on 16-11-12.
 */
public class ReleaseCorrelation {

    private static Toast toast;


    public static void handlerExceptoinHandler(Throwable e) {
        if (MyApplication.isRunning) {
            //已经发布，出错了！暂时什么都不在
            return;
        } else {
            //没有发布，暂时先打印并蹦掉
            getLog("log", e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 发布后不Log
     *
     * @param tag     标签
     * @param concent 内容
     */
    public static void getLog(String tag, String concent) {
        if (!MyApplication.isRunning) {
            Log.v(tag, concent);
        }
    }

    /**
     * 只存在一个吐司
     *
     * @param content 内容
     */
    public static void showT(String content) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.app, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();

    }


    /**
     * 自定义Toast
     *
     * @param context 上下文
     * @param mWM     窗体对象
     * @param view    自定义的布局
     *  @param content     归属地
     */
    public static void customToast(Context context, WindowManager mWM, View view, String content) {
        //样式对象
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        //属性设置：宽高
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //属性设置：
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE//不能获取焦点
//                |WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE//不能触摸
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;//屏幕开启才能看到
        //格式
        mParams.format = PixelFormat.TRANSLUCENT;
        //显示级别:和电话级别一致
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //显示位置：左上角
        mParams.gravity = Gravity.LEFT + Gravity.TOP;

        //根据用户存储的颜色设置控件背景
        Resources resources = MyApplication.app.getResources();
        int[] color = new int[]{
                resources.getColor(R.color.gray),
                resources.getColor(R.color.red),
                resources.getColor(R.color.orange),
                resources.getColor(R.color.blue),
                resources.getColor(R.color.dark_blue),
        };
        int index = SpUtils.getint(MyApplication.app, SpKeyPool.FLOAT_TOAST_COLOR, 0);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setBackgroundColor(color[index]);//颜色
        tv.setText(content);//归属地

        //挂载到窗体上【记得加权限】
        mWM.addView(view, mParams);
    }
}
