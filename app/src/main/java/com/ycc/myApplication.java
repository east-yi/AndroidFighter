package com.ycc;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.ycc.service.MyService;
import com.ycc.util.ReleaseCorrelation;

/**
 * Created by Administrator on 16-11-5.
 */
public class MyApplication extends Application{

    public static boolean isRunning=false;
    public static MyApplication app;
    public static Intent intent;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        //启动接收定位追踪短信的服务
        intent= new Intent(this, MyService.class);
        startService(new Intent(this, MyService.class));

        //注册xutil框架
//        x.Ext.init(this);
    }


}
