package com.ycc.mylibrary.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 16-11-24.
 */
public class ServiceUtils {

    /**
     * 判断某个服务是否正在运行
     * @param context
     * @param servierName 服务的名称(该服务类的完整路径)
     * @return 是否运行
     */
    public static boolean serviceRunning(Context context, String servierName) {
        ActivityManager mAMangager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得服务集合，参数：服务个数
        List<ActivityManager.RunningServiceInfo> list = mAMangager.getRunningServices(100);
        //遍历比对
        for (ActivityManager.RunningServiceInfo info : list) {
            String currentService = info.service.getClassName();
            if (servierName.equals(currentService)) {
                //存在
                return true;
            }
        }
        return false;
    }
}
