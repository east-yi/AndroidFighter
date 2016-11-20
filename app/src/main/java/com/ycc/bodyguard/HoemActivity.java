package com.ycc.bodyguard;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.ycc.adapter.gridViewAdapter;
import com.ycc.constant.Pool;
import com.ycc.constant.SpKeyPool;
import com.ycc.util.ActivityManage;
import com.ycc.util.Encrypt;
import com.ycc.util.ReleaseCorrelation;
import com.ycc.util.SpUtils;

/**
 * Created by Administrator on 16-11-16.
 */
public class HoemActivity extends ActivityManage {
    GridView gridView;
    public View view;
    private EditText etPassword1;
    private EditText etPassword0;

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
                    case 0://手机防盗
                        showDialog();
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 8://设置中心
                        HoemActivity.this.startActivity(new Intent(HoemActivity.this, FighterSet.class));
                        break;
                }
            }
        });
    }

    /**
     * 弹出输入密码对话框
     */
    public void showDialog() {
        //判断本地有没有密码
        String value = SpUtils.getString(HoemActivity.this, SpKeyPool.MOBILE_VTD, "");
        if (TextUtils.isEmpty(value)) {
            //第一次输入对话框
            onePasswordDialog();
        } else {
            //以后输入对话框
            twoPasswordDialog();
        }
    }

    private void onePasswordDialog() {
        //对话框需要的View
        view = LayoutInflater.from(this).inflate(R.layout.one_rassword_dialog, null);
        etPassword0 = (EditText) view.findViewById(R.id.et_password0);
        etPassword1 = (EditText) view.findViewById(R.id.et_password1);

        final AlertDialog diaLog = new AlertDialog.Builder(HoemActivity.this)
                .setCancelable(true)
                .setView(view)
                .show();
        view.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPassword0.getText().toString().equals(etPassword1.getText().toString())){
                    //加密
                    String passwrod=Encrypt.getInstance().stringEncrypt(etPassword0.getText().toString(),Pool.ADD_SALT);
                   // 存入Sp
                    SpUtils.putString(HoemActivity.this,SpKeyPool.MOBILE_VTD,passwrod);
                    ReleaseCorrelation.showT("设置成功！");
                    diaLog.cancel();
                    vidModule();
                }else{
                    ReleaseCorrelation.showT("输入有误！");
                }
                view=null;
            }
        });
        view.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLog.cancel();
                view=null;
            }

        });


    }

    private void twoPasswordDialog() {
        //对话框需要的View
        view = LayoutInflater.from(this).inflate(R.layout.one_rassword_dialog, null);
        etPassword0 = (EditText) view.findViewById(R.id.et_password0);
        etPassword1 = (EditText) view.findViewById(R.id.et_password1);
        etPassword1.setVisibility(View.GONE);
        final AlertDialog diaLog = new AlertDialog.Builder(HoemActivity.this)
                .setCancelable(true)
                .setView(view)
                .show();
        view.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value=SpUtils.getString(HoemActivity.this,SpKeyPool.MOBILE_VTD,"");
                //加密
                String passwrod=Encrypt.getInstance().stringEncrypt(etPassword0.getText().toString(),Pool.ADD_SALT);
                if(passwrod.equals(value)){
                    diaLog.cancel();
                    vidModule();
                }else{
                    ReleaseCorrelation.showT("密码错误！");
                }
                view=null;
            }
        });
        view.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLog.cancel();
                view=null;
            }
        });
    }

    /**
     * 跳转防盗模块
     */
    public void vidModule(){
        HoemActivity.this.startActivity(new Intent(HoemActivity.this,VTDActivity.class));
    }



}
