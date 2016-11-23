package com.ycc.bodyguard.MobileVTD;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ycc.bodyguard.R;
import com.ycc.constant.SpKeyPool;
import com.ycc.util.ActivityManage;
import com.ycc.util.SpUtils;

/**
 * 手机防盗模块
 * Created by Administrator on 16-11-20.
 *
 */
public class VTDActivity extends ActivityManage {
    private TextView tvPhone;
    private Button btnTOGuide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果设置了向导就直接进防盗主界面,没设置就进向导界面
        if(SpUtils.getBooleanS(VTDActivity.this, SpKeyPool.VTD_WIZARD,false)) {
            setContentView(R.layout.vid_activity);
            iniView();
            iniUI();
        }else{
            enterGuideLayout();
        }
    }

    private void iniView() {
        tvPhone=(TextView)findViewById(R.id.tv_safety_phone);
        btnTOGuide=(Button)findViewById(R.id.btn_to_guide);
    }

    private void iniUI() {
        String phone = SpUtils.getString(VTDActivity.this, SpKeyPool.SAFETY_PHONE, "");
        if(TextUtils.isEmpty(phone)){
            tvPhone.setText("没有安全号码");
        }else{
            tvPhone.setText(phone);
        }


        btnTOGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterGuideLayout();
            }
        });
    }




    /**
     * 进向导界面
     */
    public void enterGuideLayout(){
        VTDActivity.this.startActivity(new Intent(VTDActivity.this,Navigation0.class));
        VTDActivity.this.finish();
    }


}
