package com.ycc.bodyguard.MobileVTD;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ycc.bodyguard.R;
import com.ycc.constant.SpKeyPool;
import com.ycc.mylibrary.util.AnimUtils;
import com.ycc.util.ReleaseCorrelation;
import com.ycc.util.SpUtils;

/**
 * Created by Administrator on 16-11-20.
 */
public class Navigation3 extends NavigatManage {
    private CheckBox checkBox;
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation3);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        tv = (TextView) findViewById(R.id.tv_no);
        iniUI();

    }

    @Override
    public void showBelowPage() {
        //跳转
        if (SpUtils.getBooleanS(Navigation3.this, SpKeyPool.OPEN_SAFETY, false)) {
            Navigation3.this.startActivity(new Intent(Navigation3.this, VTDActivity.class));
            Navigation3.this.finish();
            AnimUtils.setRightAnim(Navigation3.this);
            SpUtils.putBooleanS(Navigation3.this, SpKeyPool.VTD_WIZARD, true);
        } else {
            ReleaseCorrelation.showT("请开启防盗继续操作....");
        }
    }

    @Override
    public void showTopPage() {
        //跳转
        Navigation3.this.startActivity(new Intent(Navigation3.this, Navigation2.class));

        AnimUtils.setLeftAnim(Navigation3.this);

    }


    private void iniUI() {
        boolean is = SpUtils.getBooleanS(this, SpKeyPool.OPEN_SAFETY, false);
        if (is) {
            checkBox.setChecked(true);
            tv.setText("防盗已开启！！");
        } else {
            checkBox.setChecked(false);
            tv.setText("小伙，防盗没开哟...");
        }


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //isChecked为点击后的状态
                SpUtils.putBooleanS(Navigation3.this, SpKeyPool.OPEN_SAFETY, isChecked);
                if (isChecked) {
                    tv.setText("防盗已开启！！");
                } else {
                    tv.setText("小伙，防盗没开哟...");
                }
            }
        });

    }
}
