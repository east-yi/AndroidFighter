package com.ycc.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.ycc.bodyguard.MainActivity;
import com.ycc.bodyguard.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 16-11-12.
 */
public class Resolve {
    private static Resolve resolve;

    public static Resolve getResolve() {
        if (resolve == null) {
            resolve = new Resolve();
        }
        return resolve;
    }

    /**
     * 解析流
     *
     * @param is 输入流
     * @return 返回字符串
     */
    public String resolveIs(InputStream is) {
        if (is == null) return null;
        //边读边写入内存
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int temp;
        try {
            while (!((temp = is.read(buffer)) == -1)) {
                os.write(buffer, 0, temp);
            }
            return os.toString();
        } catch (IOException e) {
            ReleaseCorrelation.handlerExceptoinHandler(e);
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                ReleaseCorrelation.handlerExceptoinHandler(e);
            }

        }
        return null;
    }

    /**
     * 下载
     */
    public void isDownload(InputStream is, FileOutputStream filePath) {
        if (is != null && filePath != null) {
            byte[] bytes = new byte[1024];
            int tep;
            try {
                while ((tep = is.read(bytes)) != -1) {
                    filePath.write(bytes, 0, tep);
                }
            } catch (Exception e) {
                ReleaseCorrelation.handlerExceptoinHandler(e);
            }
        }
    }

    /**
     * 安装Apk
     * @param apkPath apk文件
     */
    public void installApk(MainActivity activity, File apkPath) {
        Intent intent= new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(apkPath),
                "application/vnd.android.package-archive");
        //如果用户点击取消，也跳转到主界面
        activity.startActivityForResult(intent,0);
    }


    /**
     * 发通知
     * @param ticker
     * @param text
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void endNotification(Context context, String ticker, String text) {
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(ticker)
                .setContentText(text)
                .setTicker(text);
        Notification n = builder.build();
        manager.notify(NOTIFICATION_ID, n);
    }
    int NOTIFICATION_ID=11;

    /**
     * 取消通知
     */
    public void cancelNotification(Context context){
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        manager.cancel(NOTIFICATION_ID);
    }
}
