package com.ycc.bodyguard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ycc.mylibrary.util.ServiceUtils;
import com.ycc.service.ShowFloatService;
import com.ycc.ui.EntryLayout;
import com.ycc.util.ActivityManage;
import com.ycc.util.SpUtils;

/**
 * Created by Administrator on 16-11-16.
 */
public class FighterSet extends ActivityManage {
    public EntryLayout settingLayout;
    CheckBox checkBoc;
    TextView tvUpdate;
    private EntryLayout showqcellCore;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fighter_set_activity);
        iniView();
        setListener();
        iniData();
    }

    private void iniData() {
        if (SpUtils.getBoolean(FighterSet.this,"isUpdate",false)){
            checkBoc.setChecked(true);
            tvUpdate.setText("自动更新已开启");
        }else {
            checkBoc.setChecked(false);
            tvUpdate.setText("自动更新已关闭");
        }
    }


    private void iniView() {
        //自动更新设置
        settingLayout = (EntryLayout) findViewById(R.id.el_setting_update);
        checkBoc = (CheckBox) settingLayout.findViewById(R.id.checkBox);
        tvUpdate = (TextView) findViewById(R.id.tv_update_x);
        //是否显示浮动归属地
       showqcellCore = (EntryLayout) findViewById(R.id.el_show_qcellCore);
        if(ServiceUtils.serviceRunning(this,"com.ycc.service.ShowFloatService")){
            showqcellCore.setChecked(true);
        }else{
            showqcellCore.setChecked(false);
        }
    }

    private void setListener() {
        EntryLayoutOnClickListener eoc = new EntryLayoutOnClickListener();
        settingLayout.setOnClickListener(eoc);
        showqcellCore.setOnClickListener(eoc);
    }

    class EntryLayoutOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.el_setting_update:
                    checkBoc.setChecked(!settingLayout.isChecked());
                    if (checkBoc.isChecked()) {
                        tvUpdate.setText("自动更新已开启");
                        SpUtils.putBoolean(FighterSet.this,true);
                    } else {
                        tvUpdate.setText("自动更新已关闭");
                        SpUtils.putBoolean(FighterSet.this,false);
                    }
                    break;
                case R.id.el_show_qcellCore://是否显示浮动归属地
                    showqcellCore.setChecked(!showqcellCore.isChecked());
                    if(showqcellCore.isChecked()) {
                        //开启服务
                        startService(new Intent(FighterSet.this, ShowFloatService.class));
                    }else{
                        //关闭服务
                        stopService(new Intent(FighterSet.this, ShowFloatService.class));
                    }
                    break;
            }
        }
    }

}



