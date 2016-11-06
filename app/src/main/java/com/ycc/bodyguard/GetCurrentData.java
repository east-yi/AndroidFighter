package com.ycc.bodyguard;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 16-11-5.
 */
public class GetCurrentData {

    /**
     * 获得版本名称
     */
    public static String VersionsName(Context context){

        PackageManager packageName = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageName.getPackageInfo(context.getPackageName(), 0);
            return  packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获得版本号
     */
    public static int versionsNumber(Context context){
        PackageManager packageName = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageName.getPackageInfo(context.getPackageName(), 0);
            return  packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
