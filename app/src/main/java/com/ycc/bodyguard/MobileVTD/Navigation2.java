package com.ycc.bodyguard.MobileVTD;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ycc.bodyguard.R;
import com.ycc.constant.SpKeyPool;
import com.ycc.mylibrary.util.AnimUtils;
import com.ycc.util.ReleaseCorrelation;
import com.ycc.util.SpUtils;

/**
 * Created by Administrator on 16-11-20.
 */
public class Navigation2 extends NavigatManage {
    private EditText et;
    private String safetyPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation2);
        et = (EditText) findViewById(R.id.et_safety_number);
        safetyPhone = SpUtils.getString(this, SpKeyPool.SAFETY_PHONE, "");
        if (!TextUtils.isEmpty(safetyPhone)) {
            et.setText(safetyPhone);
        }

    }

    @Override
    public void showBelowPage() {
        //跳转
        String string = SpUtils.getString(Navigation2.this, SpKeyPool.SAFETY_PHONE, "");
        if (TextUtils.isEmpty(string)) {
            ReleaseCorrelation.showT("请从联系人选择安全号码。。。");
            return;
        }
        if (et.getText().toString().equals(string)) {
            Navigation2.this.startActivity(new Intent(Navigation2.this, Navigation3.class));
            Navigation2.this.finish();
            AnimUtils.setRightAnim(Navigation2.this);

        }else {
            ReleaseCorrelation.showT("请从联系人选择安全号码。。。");
        }
    }

    @Override
    public void showTopPage() {
        //跳转
        Navigation2.this.startActivity(new Intent(Navigation2.this, Navigation1.class));
        AnimUtils.setLeftAnim(Navigation2.this);
    }

    public void click(View view) {
        if (view.getId() == R.id.btn_linkman) {
            //选择联系人，跳转选择联系人界面
            startActivityForResult(new Intent(this, LinkmanActivity.class), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (data != null) {
                    //接收号码
                    String phone = data.getStringExtra("phone");
                    //存起来
                    SpUtils.putString(Navigation2.this, SpKeyPool.SAFETY_PHONE, phone);
                    //显示再et上
                    et.setText(phone);
                }
                break;
        }
    }
}
