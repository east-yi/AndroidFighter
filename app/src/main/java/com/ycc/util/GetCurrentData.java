package com.ycc.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;

import com.ycc.constant.MessagePool;
import com.ycc.constant.Pool;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 16-11-5.
 */
public class GetCurrentData {

    /**
     * 获得服务器版本号
     */
    public static void getServiceVersion(final Handler handler) {

        new Thread(new Runnable() {
            HttpURLConnection http = null;
            InputStream is = null;

            @Override
            public void run() {
                try {
                    URL url = new URL(Pool.SERVIE_VERSION_INFO);
                    http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("GET");
                    http.setConnectTimeout(5000);
                    http.setReadTimeout(5000);
                    if (http.getResponseCode() == 200) {
                        is = http.getInputStream();
                        //流转换成字符串
                        String json = Resolve.getResolve().resolveIs(is);
                        //解析完成，发消息
                        Message message = handler.obtainMessage();
                        message.arg1 = MessagePool.IS_RESOLVE_OK;
                        message.obj = json;
                        handler.sendMessage(message);
                    } else {
                        ReleaseCorrelation.showT("请求失败！");
                    }
                } catch (MalformedURLException e) {
                    ReleaseCorrelation.handlerExceptoinHandler(e);
                } catch (IOException e) {
                    ReleaseCorrelation.handlerExceptoinHandler(e);
                } finally {
                    if (http != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            ReleaseCorrelation.handlerExceptoinHandler(e);
                        }
                        http.disconnect();
                    }
                }
            }
        }).start();

    }

    /**
     * 获得版本名称
     */
    public static String versionsName(Context context) {

        PackageManager packageName = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageName.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获得版本号
     */
    public static float versionsNumber(Context context) {
        PackageManager packageName = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageName.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
