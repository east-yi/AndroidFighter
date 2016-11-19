package com.ycc.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 16-11-19.
 */
public class SpUtils {
    private static SharedPreferences sp;

    //写
    public static void putBoolean(Context context,boolean value){
            if(sp==null){
                sp=context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
            }
        sp.edit().putBoolean("isUpdate",value).commit();
    }
    //读
    public static boolean getBoolean(Context context, String key, boolean defValue){
        if(sp==null){
            sp=context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defValue);
    }
}
