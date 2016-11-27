package com.ycc.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.ycc.broadcast.SMSBroadcast;
import com.ycc.util.ReleaseCorrelation;

/**
 * Created by Administrator on 16-11-22.
 */
public class MyService extends Service {

    private String phone;

    @Override
    public void onCreate() {
        //设置为前台进程
        Notification notification = new Notification();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
        this.startForeground(1, notification);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //获取对方号码
        phone = SMSBroadcast.phone;
        //定位
        if (phone != null && phone != "") {
            getLocation(this);
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 用最优的定位方式，获得经度、纬度：记得加权限！
     *
     * @param context
     */
    public void getLocation(Context context) {
        //位置管理对象
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        //最优定位方式
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true);//允许使用流量
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //指定获取经纬度的精确度(精确度越高相应的越耗电)
        String actor = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(actor);
        //发送短信给对方
        SmsManager sm = SmsManager.getDefault();
        if(location==null) {
            //记得加权限
            sm.sendTextMessage(String.valueOf(phone), null, "定位失败！", null, null);
            ReleaseCorrelation.showT("定位失败！");
        }else{
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            //记得加权限
            sm.sendTextMessage(String.valueOf(phone), null, "经度：" + longitude + "\n纬度：" + latitude, null, null);
        }


        //开始定位
//        locationManager.requestLocationUpdates(actor, 0, 0, new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                double longitude = location.getLongitude();
//                double latitude = location.getLatitude();
//                //发送短信给对方
//                SmsManager sm = SmsManager.getDefault();
//                //记得加权限
//                sm.sendTextMessage(String.valueOf(phone), null, "经度："+longitude+"\n纬度:"+latitude, null, null);
//                //发送一条就行了
//                return;
//            }
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//                //定位状态发生切换的事件监听
//            }
//            @Override
//            public void onProviderEnabled(String provider) {
//                //定位方式开启的时候的监听
//            }
//            @Override
//            public void onProviderDisabled(String provider) {
//                //关闭的时候的监听
//            }
//        });
    }


    /**
     * 用最优的定位方式，获得经度、纬度：【记得加权限】
     * @param context
     * @return 经度、纬度
     */
    public double[] getPosition(Context context) {
        //位置管理对象
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        //最优定位方式
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true);//允许使用流量
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //指定获取经纬度的精确度(精确度越高相应的越耗电)
        String actor = locationManager.getBestProvider(criteria, true);
        //开始定位
        Location location = locationManager.getLastKnownLocation(actor);
        if (location == null) {
            //失败
            Toast.makeText(context,"请检查定位服务是否开启",Toast.LENGTH_LONG).show();
        } else {
            //经度
            double longitude = location.getLongitude();
            //纬度
            double latitude = location.getLatitude();
            //装，返回
            return new double[]{longitude,latitude};
        }
        //定位失败返回null
        return null;
    }
}