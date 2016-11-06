package com.ycc.bodyguard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvVersions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        //初始化数据
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        tvVersions.setText(GetCurrentData.VersionsName(this));
    }

    /**
     * 初始化控件
     */
    private void initView() {
        tvVersions = (TextView) findViewById(R.id.tv_versions);
    }


}
