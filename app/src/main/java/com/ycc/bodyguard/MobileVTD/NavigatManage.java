package com.ycc.bodyguard.MobileVTD;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.ycc.bodyguard.R;
import com.ycc.util.ActivityManage;

/**
 * 管理导航Activity的父类
 * Created by Administrator on 16-11-22.
 */
public abstract class NavigatManage extends ActivityManage {

    private GestureDetector gesture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation0);
        gesture=new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                if (e1.getRawX()-e2.getRawX()>100) {//向左滑动
                    //执行上一页业务
                    showBelowPage();

                }else if (e2.getRawX()-e1.getRawX()>100) {//向右滑动
                    //执行下一页业务
                    showTopPage();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //通过手势识别器去识别不同的事件类型
        gesture.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     *  当前监听Activity手势
     */


    //下一页，由子类实现
    public abstract void showBelowPage();
    //上一页，由子类实现
    public abstract void showTopPage();

    //点击事件
    public void doClick(View view){
        if(view.getId()==R.id.btn_next){//下
            showBelowPage();
        }else if(view.getId()==R.id.btn_top){//上
            showTopPage();
        }
    };

}
