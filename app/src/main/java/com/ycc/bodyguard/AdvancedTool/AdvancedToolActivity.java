package com.ycc.bodyguard.AdvancedTool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ycc.bodyguard.R;
import com.ycc.util.ActivityManage;

/**
 * 第七个模块，高级工具Acitivity
 * Created by Administrator on 16-11-23.
 */
public class AdvancedToolActivity extends ActivityManage{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atool);
        //电话归属地查询方法
        initPhoneAddress();
    }

    private void initPhoneAddress() {
        InnerOnClickListener ioc = new InnerOnClickListener();
        findViewById(R.id.tv_query_phone_address).setOnClickListener(ioc);
    }


    class  InnerOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
               case  R.id.tv_query_phone_address://号码归属地查询
                 startActivity(new Intent(AdvancedToolActivity.this, QueryAddressActivity.class));
                break;
            }

        }
    }
}
