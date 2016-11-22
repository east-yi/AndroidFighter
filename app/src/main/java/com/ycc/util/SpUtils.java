package com.ycc.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 16-11-19.
 */
public class SpUtils {
    private static SharedPreferences sp;

    //写Boolean
    public static void putBoolean(Context context,boolean value){
        if(sp==null){
            sp=context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean("isUpdate",value).commit();
    }
    //读Boolean
    public static boolean getBoolean(Context context, String key, boolean defValue){
        if(sp==null){
            sp=context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defValue);
    }


    //写String
    public static void putString(Context context,String key,String value){
        if(sp==null){
            sp=context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).commit();
    }
    //读String
    public static String getString(Context context, String key, String defValue){
        if(sp==null){
            sp=context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
        }
        return sp.getString(key,defValue);
    }


    //写BooleanS
    public static void putBooleanS(Context context,String key,boolean value){
        if(sp==null){
            sp=context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }
    //读BooleanS
    public static boolean getBooleanS(Context context, String key, boolean defValue){
        if(sp==null){
            sp=context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defValue);
    }


    /**
     * 删除节点
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        if(sp==null){
            sp=context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
        }
       sp.edit().remove(key).commit();
    }
}
