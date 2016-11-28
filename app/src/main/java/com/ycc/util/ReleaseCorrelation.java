package com.ycc.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
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
    public static void customToast(final Context context, final WindowManager mWM, final View view, String content) {
        //屏幕的宽高
        final int w = mWM.getDefaultDisplay().getWidth();
        final int h = mWM.getDefaultDisplay().getHeight();
        final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        //属性设置：宽高
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //属性设置：
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE//不能获取焦点
//                |WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE//不能触摸
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;//屏幕开启才能看到
        //格式
        mParams.format = PixelFormat.TRANSLUCENT;
        //显示级别:和Toast级别一致
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        //显示位置：左上角
        mParams.gravity = Gravity.LEFT + Gravity.TOP;
        mParams.x=SpUtils.getint(context, SpKeyPool.TOAST_X_POSITION, 0);
        mParams.y=SpUtils.getint(context, SpKeyPool.TOAST_Y_POSITION, 0);
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
        //控件拖拽监听
        view.setOnTouchListener(new View.OnTouchListener() {
            public int startY;
            public int startX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    //按下(执行1次)
                    case MotionEvent.ACTION_DOWN:
                        //手指按下时的那个坐标：离左边界、上边界的距离
                        startX = (int) event.getRawX();
                        startY  = (int) event.getRawY();
                        break;
                    //移动(执行N次)
                    case MotionEvent.ACTION_MOVE:
                        //1.手指滑动后那个坐标：距离左边界、上边界的距离
                        int moveX = (int) event.getRawX();
                        int moveY = (int) event.getRawY();
                        //2.滑动后-按下时=手指移动了多少距离（X、Y）
                        int moveLengthX = moveX - startX;
                        int moveLengthY = moveY - startY;
                        //3.算出移动了多少距离之后，
                        //就可以把控件当前所在位置的上下边+手指移动的Y距离;左右边+手指移动的X距离,这样移动后控件应该出现的位置就很明确了
                        mParams.x+=moveLengthX;
                        mParams.y+=moveLengthY;
                        //按个人需要增添：以下判断是防止控件被拖出屏幕(可以先不加然后看看效果)
                        if (mParams.x <0) mParams.x=0;//左
                        if (mParams.y <0) mParams.y=0;//上
                        if(mParams.x>w-view.getWidth())mParams.x=w-view.getWidth();//右
                        if(mParams.y>h-view.getHeight()-70)mParams.y=h-view.getHeight()-70;//下
                        //5.把这个位置又作为起始坐标
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        //更新到窗体上
                        mWM.updateViewLayout(view, mParams);
                        break;
                    //抬起(执行1次)
                    case MotionEvent.ACTION_UP:
                        //本次监听结束：把最后的位置存起来(如果你不做回显就不用存)
                        SpUtils.putint(context, SpKeyPool.TOAST_X_POSITION, mParams.x);
                        SpUtils.putint(context, SpKeyPool.TOAST_Y_POSITION, mParams.y);
                        break;
                }
                return true;
            }
        });
    }
}
