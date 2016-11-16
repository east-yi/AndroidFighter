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
    private final CheckBox checkBox;

    public EntryLayout(Context context) {
        this(context, null);
    }

    public EntryLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EntryLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //讲一个布局界面转换成View对象
        View.inflate(context, R.layout.entry_layout, this);
        TextView tvUpdate = (TextView) findViewById(R.id.tv_update_x);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
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
    }


}
