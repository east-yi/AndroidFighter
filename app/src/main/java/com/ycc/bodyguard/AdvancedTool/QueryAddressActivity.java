package com.ycc.bodyguard.AdvancedTool;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.ycc.MyApplication;
import com.ycc.bodyguard.R;
import com.ycc.db.AddressDao;
import com.ycc.util.ActivityManage;

/**
 * 号码归属地查询Acitivit
 * Created by Administrator on 16-11-23.
 */
public class QueryAddressActivity extends ActivityManage {

    private EditText etPhone;
    private TextView tvShow;
    public String mAddress;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //4,控件使用查询结果
            tvShow.setText(mAddress);
        }

        ;
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_address_activity);
        etPhone = (EditText) findViewById(R.id.et_phone);
        tvShow = (TextView) findViewById(R.id.tv_show);

        //实时查询
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = etPhone.getText().toString();
                query(phone);
            }
        });
    }

    public void doClick(View view) {
        String phone = etPhone.getText().toString();
        if(TextUtils.isEmpty(phone)) {
            //抖动
            Animation shake = AnimationUtils.loadAnimation(MyApplication.app, R.anim.shake);
            etPhone.startAnimation(shake);
            //震动
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            //震动两秒
            vibrator.vibrate(1000);
            //r震动规律
//            vibrator.vibrate(new long[]{1000,1000,1000,1000},-1);

        }else{
            //查询
            query(phone);
        }
    }

    /**
     * 耗时操作
     * 获取电话号码归属地
     *
     * @param phone 查询电话号码
     */
    protected void query(final String phone) {

        new Thread() {
            public void run() {
                mAddress = AddressDao.queryPhone(phone);
                //消息机制,告知主线程查询结束,可以去使用查询结果
                mHandler.sendEmptyMessage(0);
            };
        }.start();
    }
}
