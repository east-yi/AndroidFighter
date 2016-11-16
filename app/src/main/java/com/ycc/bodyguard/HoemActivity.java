package com.ycc.bodyguard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ycc.adapter.gridViewAdapter;
import com.ycc.util.ActivityManage;

/**
 * Created by Administrator on 16-11-16.
 */
public class HoemActivity extends ActivityManage {
    GridView gridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoem);
        iniView();
        iniData();
        setListener();
    }


    private void iniView() {
        gridView = (GridView) findViewById(R.id.gridView);
    }

    private void iniData() {
        String[] arrayString = new String[]{
                "手机防盗", "通讯卫士", "软件管理", "进程管理", "流量管理",
                "手机杀毒", "缓存管理", "高级工具", "设置中心"
        };
        int[] arrayImage = new int[]{
                R.drawable.home_safe, R.drawable.home_callmsgsafe,
                R.drawable.home_apps, R.drawable.home_taskmanager,
                R.drawable.home_netmanager, R.drawable.home_trojan,
                R.drawable.home_sysoptimize, R.drawable.home_tools,
                R.drawable.home_settings
        };
        gridViewAdapter adapter = new gridViewAdapter(HoemActivity.this, arrayImage, arrayString);
        gridView.setAdapter(adapter);
    }


    private void setListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 8://卫士设置
                        HoemActivity.this.startActivity(new Intent(HoemActivity.this,FighterSet.class));
                        break;
                }
            }
        });
    }
}
