package com.ycc.mylibrary.util;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 16-11-28.
 */
public class ClickEvent {


    /**
     * 监听控件连续点击2次
     * @param view         控件
     * @param intervalTime 多少时间内有效
     */
    public static void  listenerClick2(final Context context,View view, final long intervalTime) {
        view.setOnClickListener(new View.OnClickListener() {
            public long clickTime;

            @Override
            public void onClick(View v) {
                if (clickTime != 0) {
                    if (System.currentTimeMillis() - clickTime <= intervalTime) {
                        Toast.makeText(context, "我被连续点击2次啦！", Toast.LENGTH_SHORT).show();
                    }
                }
                clickTime = System.currentTimeMillis();
            }
        });
    }

    /**
     * 监听控件连续点击N次
     * @param view         控件
     * @param amount       点击次数
     * @param intervalTime 多少时间内有效
     */
    public static void listenerClickN(final Context context, View view, final int amount, final long intervalTime) {
        final long[] amountArray=new long[amount];
        view.setOnClickListener(new View.OnClickListener() {
            public long clickTime;
            @Override
            public void onClick(View v) {
                System.arraycopy(amountArray,1,amountArray,0,amountArray.length-1);
                amountArray[amountArray.length-1]= SystemClock.uptimeMillis();
                if(amountArray[amountArray.length-1]-amountArray[0]<=intervalTime){
                    Toast.makeText(context,"受不了啦！我被连续点击"+amount+"次啦！！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
