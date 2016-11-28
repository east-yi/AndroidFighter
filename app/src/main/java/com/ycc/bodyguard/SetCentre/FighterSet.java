package com.ycc.bodyguard.SetCentre;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ycc.bodyguard.R;
import com.ycc.constant.SpKeyPool;
import com.ycc.mylibrary.util.ServiceUtils;
import com.ycc.service.ShowFloatService;
import com.ycc.ui.ArrowsLayout;
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
    private ArrowsLayout arrows;
    private int colorIndex;
    private String[] arrayColor;
    private ArrowsLayout toastPosition;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fighter_set_activity);
        iniView();
        setListener();

        iniData();
    }

    private void iniData() {

        //1.是否检查更新
        if (SpUtils.getBoolean(FighterSet.this, "isUpdate", false)) {
            checkBoc.setChecked(true);
            tvUpdate.setText("自动更新已开启");
        } else {
            checkBoc.setChecked(false);
            tvUpdate.setText("自动更新已关闭");
        }
        //3.设置浮动归属地View的颜色
        arrows.setTvTitle("设置来电归属地的样式");
        arrayColor = new String[]{"灰色","红色", "橙色", "绿色", "蓝色"};
        colorIndex = SpUtils.getint(this, SpKeyPool.FLOAT_TOAST_COLOR, 0);
        arrows.setTvColor(arrayColor[colorIndex]);
    }


    private void iniView() {
        //1.自动更新设置
        settingLayout = (EntryLayout) findViewById(R.id.el_setting_update);
        checkBoc = (CheckBox) settingLayout.findViewById(R.id.checkBox);
        tvUpdate = (TextView) findViewById(R.id.tv_update_x);
        //2.是否显示浮动归属地
        showqcellCore = (EntryLayout) findViewById(R.id.el_show_qcellCore);
        if (ServiceUtils.serviceRunning(this, "com.ycc.service.ShowFloatService")) {
            showqcellCore.setChecked(true);
        } else {
            showqcellCore.setChecked(false);
        }
        //3.设置浮动颜色
        arrows = (ArrowsLayout) findViewById(R.id.al_color);
        //4.设置Toast位置
        toastPosition = (ArrowsLayout) findViewById(R.id.al_position);
        toastPosition.setTvTitle("归属地提示框位置");
        toastPosition.setTvColor("设置归属地提示框位置");
    }

    private void setListener() {
        EntryLayoutOnClickListener eoc = new EntryLayoutOnClickListener();
        settingLayout.setOnClickListener(eoc);
        showqcellCore.setOnClickListener(eoc);
        arrows.setOnClickListener(eoc);
        toastPosition.setOnClickListener(eoc);
    }

    class EntryLayoutOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.el_setting_update:
                    checkBoc.setChecked(!settingLayout.isChecked());
                    if (checkBoc.isChecked()) {
                        tvUpdate.setText("自动更新已开启");
                        SpUtils.putBoolean(FighterSet.this, true);
                    } else {
                        tvUpdate.setText("自动更新已关闭");
                        SpUtils.putBoolean(FighterSet.this, false);
                    }
                    break;
                case R.id.el_show_qcellCore://是否显示浮动归属地
                    showqcellCore.setChecked(!showqcellCore.isChecked());
                    if (showqcellCore.isChecked()) {
                        //开启服务
                        startService(new Intent(FighterSet.this, ShowFloatService.class));
                    } else {
                        //关闭服务
                        stopService(new Intent(FighterSet.this, ShowFloatService.class));
                    }
                    break;
                case R.id.al_color://设置Toast颜色
                    //弹出选择颜色对话框
                    showColorDialog();
                    break;
                case R.id.al_position://设置Toast位置
                    //跳转界面
                    FighterSet.this.startActivity(new Intent(FighterSet.this,ToastPosition.class));
            }
        }
    }

    /**
     * 选择颜色对话框
     */
    private void showColorDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(FighterSet.this);

        builder.setIcon(R.drawable.ic_launcher)
                .setTitle("请选择归属地颜色")
                .setSingleChoiceItems(arrayColor, colorIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //点击某个颜色执行：1.存入索引到SP,2.改变控件文字，3.关闭对话
                        SpUtils.putint(FighterSet.this, SpKeyPool.FLOAT_TOAST_COLOR, which);
                        arrows.setTvColor(arrayColor[which]);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}



