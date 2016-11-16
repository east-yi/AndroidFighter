package com.ycc.bodyguard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.ycc.constant.MessagePool;
import com.ycc.constant.Pool;
import com.ycc.util.ActivityManage;
import com.ycc.util.GetCurrentData;
import com.ycc.util.ReleaseCorrelation;
import com.ycc.util.Resolve;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends ActivityManage {

    String newVersionName;
    String newContent;
    String newVersiongCode;
    String ApkUrl;
    private TextView tvVersions;
    private float versionNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        //初始化数据
        initData();
        //初始动画效果
        inianimation();
    }

    private void inianimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(3000);
        findViewById(R.id.main_rl).setAnimation(alphaAnimation);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        skipHomeActivity(MainActivity.this);
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case MessagePool.IS_RESOLVE_OK:
                    String json = msg.obj.toString();
                   analysisJson(json);
                    break;
            }
        }
    };

    /**
     * 初始化数据
     */
    private void initData() {
        //当前版本名称
        tvVersions.setText(GetCurrentData.versionsName(this));
        versionNumber = GetCurrentData.versionsNumber(this);
        //服务器版本
        GetCurrentData.getServiceVersion(handler);

    }

    /**
     * 初始化控件
     */
    private void initView() {
        tvVersions = (TextView) findViewById(R.id.tv_versions);
    }

    public void skipHomeActivity(MainActivity activity){
        activity.startActivity(new Intent(activity,HoemActivity.class));
        activity.finish();
    }

    /**
     * 解析服务器数据
     */
    public void analysisJson(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            newVersionName = obj.getString("versionName");
            newContent = obj.getString("versionDes");
            newVersiongCode = obj.getString("versiongCode");
            ApkUrl = obj.getString("downlaoadUrl");
            if (Float.valueOf(newVersiongCode) > versionNumber) {
                //弹出对话框
                AlertDialog.Builder buider = new AlertDialog.Builder(this);
                buider.setIcon(R.drawable.ic_launcher)
                        .setTitle("有最新版本")
                        .setMessage(newContent)
                        .setPositiveButton("更新", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //下载APK
                                downloadNewApk(ApkUrl, handler);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //跳转UI
                                skipHomeActivity(MainActivity.this);
                            }
                        }).show();

            } else {
                ReleaseCorrelation.showT("已是最新版本！");
            }
        } catch (JSONException e) {
            ReleaseCorrelation.handlerExceptoinHandler(e);
        }
    }

    /**
     * 下载最新版本Apk
     *
     * @param apkUrl Apk地址
     */
    private void downloadNewApk(final String apkUrl, Handler handler) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    //准备工作
                    File apkPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), Pool.APK_POSITION);
                    if (!apkPath.getParentFile().exists()) apkPath.getParentFile().mkdirs();
                    if (!apkPath.exists()) apkPath.createNewFile();
                    FileOutputStream os = new FileOutputStream(apkPath);
                    //开始下载
                    URL url = new URL(apkUrl);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("GET");
                    http.setConnectTimeout(5000);
                    if (http.getResponseCode() == 200) {
                        //开始下载，发个通知
                        Resolve.getResolve().endNotification(MainActivity.this, "开始下载", "下载咯");
                        InputStream is = http.getInputStream();
                        Resolve.getResolve().isDownload(is, os);
                        //下载完成，取消通知
                        Resolve.getResolve().cancelNotification(MainActivity.this);
                        //开始安装APK
                        Resolve.getResolve().installApk(MainActivity.this, apkPath);
                        //TODO 改配置文件的版本号
                    } else {
                        ReleaseCorrelation.showT("下载请求失败！");
                    }

                } catch (Exception e) {
                    ReleaseCorrelation.handlerExceptoinHandler(e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
            }
        };

        task.execute();
    }

}
