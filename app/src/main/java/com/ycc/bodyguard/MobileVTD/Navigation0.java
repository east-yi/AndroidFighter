package com.ycc.bodyguard.MobileVTD;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ycc.bodyguard.R;
import com.ycc.mylibrary.util.AnimUtils;

/**
 * Created by Administrator on 16-11-20.
 */
public class Navigation0 extends NavigatManage{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation0);
    }

    @Override
    public void showBelowPage() {
        //跳转
        Navigation0.this.startActivity(new Intent(Navigation0.this,Navigation1.class));
        Navigation0.this.finish();
        AnimUtils.setRightAnim(Navigation0.this);
    }

    @Override
    public void showTopPage() {

    }

    public void doClick(View view){
        if(view.getId()==R.id.btn_next){
            //跳转
            Navigation0.this.startActivity(new Intent(Navigation0.this,Navigation1.class));
            Navigation0.this.finish();
            AnimUtils.setRightAnim(Navigation0.this);
        }
    }

}
