package com.ycc.bodyguard.app;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 16-11-5.
 */
public class myApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //注册xutil框架
        x.Ext.init(this);
    }
}
