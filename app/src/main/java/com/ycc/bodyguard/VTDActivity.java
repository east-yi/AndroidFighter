package com.ycc.bodyguard;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ycc.util.ActivityManage;

/**
 * 手机防盗模块
 * Created by Administrator on 16-11-20.
 *
 */
public class VTDActivity extends ActivityManage {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vid_activity);
    }
}
