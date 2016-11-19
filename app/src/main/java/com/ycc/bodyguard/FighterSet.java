package com.ycc.bodyguard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

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
    }

    private void setListener() {
        EntryLayoutOnClickListener eoc = new EntryLayoutOnClickListener();
        settingLayout.setOnClickListener(eoc);
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
            }
        }
    }

}



