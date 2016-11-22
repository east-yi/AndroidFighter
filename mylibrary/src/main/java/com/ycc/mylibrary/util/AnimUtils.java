package com.ycc.mylibrary.util;

import android.app.Activity;

import com.ycc.mylibrary.R;

/**
 * Created by Administrator on 16-11-21.
 */
public class AnimUtils {
    /**
     * 左入左出动画
     * @param activity
     */
    public static void setLeftAnim(Activity activity){
        activity.overridePendingTransition(R.anim.left_enter,R.anim.left_out);
    }
    /**
     * 右入右出动画
     */
    public  static void setRightAnim(Activity activity) {
        activity.overridePendingTransition(R.anim.right_enter, R.anim.right_out);
    }


    /**
     * 下入下出动画
     */
    public  static void setBelowAnim(Activity activity) {
        activity.overridePendingTransition(R.anim.below_enter, R.anim.below_out);
    }

}
