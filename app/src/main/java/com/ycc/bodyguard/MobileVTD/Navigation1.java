package com.ycc.bodyguard.MobileVTD;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ycc.bodyguard.R;
import com.ycc.constant.SpKeyPool;
import com.ycc.mylibrary.util.AnimUtils;
import com.ycc.ui.EntryLayout;
import com.ycc.util.ReleaseCorrelation;
import com.ycc.util.SpUtils;

/**
 * Created by Administrator on 16-11-20.
 */
public class Navigation1 extends NavigatManage implements View.OnClickListener{
    private EntryLayout etlBdSim;
    private TextView tvX;
    private CheckBox checkBox;
    private String value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation1);
        iniView();
        iniData();
    }

    @Override
    public void showBelowPage() {
        //跳转
        if(etlBdSim.isChecked()) {
            Navigation1.this.startActivity(new Intent(Navigation1.this, Navigation2.class));
            Navigation1.this.finish();
            AnimUtils.setRightAnim(Navigation1.this);

        }else{
            ReleaseCorrelation.showT("请绑定SIM卡继续操作。。");
        }
    }

    @Override
    public void showTopPage() {
        Navigation1.this.startActivity(new Intent(Navigation1.this, Navigation0.class));
        AnimUtils.setLeftAnim(Navigation1.this);
    }

    private void iniData() {
        value=SpUtils.getString(this,SpKeyPool.IS_DBSIM,"");
        if (TextUtils.isEmpty(value)) {
           etlBdSim.setChecked(false);
        }else{
            etlBdSim.setChecked(true);
        }
    }

    private void iniView() {
        etlBdSim =(EntryLayout) findViewById(R.id.etl_bdSim);
        checkBox=(CheckBox)etlBdSim.findViewById(R.id.checkBox);
        tvX = (TextView) etlBdSim.findViewById(R.id.tv_update_x);
        etlBdSim.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etl_bdSim:
                etlBdSim.setChecked(!etlBdSim.isChecked());
                if (etlBdSim.isChecked()) {
                    //获取SIM卡号
                    TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    //存
                    SpUtils.putString(Navigation1.this,SpKeyPool.IS_DBSIM,telephony.getSimSerialNumber());
                }else{
                    //删除节点
                    SpUtils.remove(Navigation1.this,SpKeyPool.IS_DBSIM);
                }
                break;
        }
    }
}
