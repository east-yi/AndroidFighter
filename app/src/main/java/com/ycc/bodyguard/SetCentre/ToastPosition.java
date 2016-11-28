package com.ycc.bodyguard.SetCentre;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ycc.bodyguard.R;
import com.ycc.constant.SpKeyPool;
import com.ycc.util.ActivityManage;
import com.ycc.util.SpUtils;

/**
 * Created by Administrator on 16-11-27.
 */
public class ToastPosition extends ActivityManage {

    private ImageView ivToast;
    private Button btnS;
    private Button btnX;
    private int w;
    private int h;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toast_position);

        //获得整个屏幕的宽、高
        WindowManager wWM = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        w = wWM.getDefaultDisplay().getWidth();
        h = wWM.getDefaultDisplay().getHeight();
        iniView();
        iniListener(ivToast);
    }


    private void iniView() {
        ivToast = (ImageView) findViewById(R.id.iv_toast);
        btnS = (Button) findViewById(R.id.btn_s);
        btnX = (Button) findViewById(R.id.btn_x);


        //获取之前的位置
        int toastX = SpUtils.getint(this, SpKeyPool.TOAST_X_POSITION, 0);
        int toastY = SpUtils.getint(this, SpKeyPool.TOAST_Y_POSITION, 0);
        //规则
        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rllp.leftMargin = toastX;
        rllp.topMargin = toastY;
        //将以上规则作用在ivToast上
        ivToast.setLayoutParams(rllp);
        if (toastY > h / 2 - ivToast.getHeight() / 2) {
            btnS.setVisibility(View.VISIBLE);
            btnX.setVisibility(View.GONE);
        } else {
            btnX.setVisibility(View.VISIBLE);
            btnS.setVisibility(View.GONE);
        }
    }


    /**
     * 监听控件移动状态
     *
     * @param view 控件
     */
    private void iniListener(final View view) {
        //监听点击
        view.setOnClickListener(new View.OnClickListener() {
            public long clickTime;

            @Override
            public void onClick(View v) {

                if (clickTime != 0) {
                    if (System.currentTimeMillis() - clickTime <= 500) {
                        //控件居中
                        int left = w / 2 - view.getWidth() / 2;
                        int top = (h-70)/ 2 - view.getHeight() / 2;
                        int right = w / 2 + view.getWidth() / 2;
                        int below = (h-70)/ 2 + view.getHeight() / 2;
                        view.layout(left,top,right,below);
                        //存起来
                        SpUtils.putint(ToastPosition.this, SpKeyPool.TOAST_X_POSITION, view.getLeft());
                        SpUtils.putint(ToastPosition.this, SpKeyPool.TOAST_Y_POSITION, view.getTop());
                    }
                }
                clickTime = System.currentTimeMillis();
            }
        });
        //监听移动
        view.setOnTouchListener(new View.OnTouchListener() {
            private int startX;
            private int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    //按下(执行1次)
                    case MotionEvent.ACTION_DOWN:
                        //手指按下时的那个坐标：离左边界、上边界的距离
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
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
                        int left = view.getLeft() + moveLengthX;
                        int top = view.getTop() + moveLengthY;
                        int right = view.getRight() + moveLengthX;
                        int bottom = view.getBottom() + moveLengthY;
                        //按个人需要增添：以下判断是防止控件被拖出屏幕(可以先不加然后看看效果)
                        if (bottom > h - 70 || left < 0 || top < 0 || right > w) {
                            //减70是减去手机最上面的状态栏的高度，不同手机状态栏高度不同，自己调节
                            return true;
                        }
                        if (top > h / 2 - view.getHeight() / 2) {
                            btnS.setVisibility(View.VISIBLE);
                            btnX.setVisibility(View.INVISIBLE);
                        } else {
                            btnX.setVisibility(View.VISIBLE);
                            btnS.setVisibility(View.INVISIBLE);
                        }
                        //4.把控件定位到移动后的位置
                        view.layout(left, top, right, bottom);

                        //5.把这个位置又作为起始坐标
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    //抬起(执行1次)
                    case MotionEvent.ACTION_UP:
                        //本次监听结束：把最后的位置存起来(如果你不做回显就不用存)
                        SpUtils.putint(ToastPosition.this, SpKeyPool.TOAST_X_POSITION, view.getLeft());
                        SpUtils.putint(ToastPosition.this, SpKeyPool.TOAST_Y_POSITION, view.getTop());
                        break;
                }
                //记得改成true哟
                return false;
            }
        });
    }

}
