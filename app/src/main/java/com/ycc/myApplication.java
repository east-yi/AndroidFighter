package com.ycc;

import android.app.Application;

/**
 * Created by Administrator on 16-11-5.
 */
public class MyApplication extends Application{

    public static boolean isRunning=false;
    public static MyApplication app;
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        //注册xutil框架
//        x.Ext.init(this);
    }
}
