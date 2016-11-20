package com.ycc.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ycc.bodyguard.R;

/**
 * Created by Administrator on 16-11-16.
 */
public class EntryLayout extends RelativeLayout {
    private final String NAME_INTERSPACE = "http://schemas.android.com/apk/res/com.ycc.bodyguard";
    private final CheckBox checkBox;
    private String belowon;
    private String belowff;
    private String title;
    TextView tvTitle;
    TextView tvUpdate;

    public EntryLayout(Context context) {
        this(context, null);
    }

    public EntryLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EntryLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //将一个布局界面转换成View对象
        View.inflate(context, R.layout.entry_layout, this);
        tvTitle = (TextView) findViewById(R.id.tv_update_s);
        tvUpdate = (TextView) findViewById(R.id.tv_update_x);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        //获取自定义以及原生属性的值
        initAttrs(attrs);
        //把获取的值赋值控件
        iniData();


    }

    /**
     * 获取自定义以及原生属性的值
     */
    public void initAttrs(AttributeSet attrs) {
        //属性总个数
//        int count=attrs.getAttributeCount();
        //依次获得每一个属性
//        for (int i=0;i<attrs.getAttributeCount();i++){
//            //属性名
//            attrs.getAttributeName(i);
//            //属性值
//            attrs.getAttributeValue(i);
//        }
        title = attrs.getAttributeValue(NAME_INTERSPACE, "titleName");
        belowff = attrs.getAttributeValue(NAME_INTERSPACE, "belowff");
        belowon = attrs.getAttributeValue(NAME_INTERSPACE, "belowon");
    }

    ;

    /**
     * 赋值给控件
     */
    private void iniData() {
        if (title != null) tvTitle.setText(title);

        if (belowon != null) {
            tvUpdate.setText(belowon);
            checkBox.setChecked(true);
        }
        if (belowff != null){
            tvUpdate.setText(belowff);
            checkBox.setChecked(false);
        }
    }




    /**
     * 判断是否选中
     */
    public boolean isChecked() {
        return checkBox.isChecked();
    }

    /**
     * 点击调用,设置是否选中，点击调用
     */
    public void setChecked(boolean isChecked) {

        checkBox.setChecked(isChecked);
        if (checkBox.isChecked()) {
            tvUpdate.setText(belowon);
        } else {
            tvUpdate.setText(belowff);
        }
    }


}
