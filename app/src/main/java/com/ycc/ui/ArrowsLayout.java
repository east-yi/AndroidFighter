package com.ycc.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ycc.bodyguard.R;

/**
 * Created by Administrator on 16-11-27.
 */
public class ArrowsLayout extends RelativeLayout {
    private final TextView tvTitle;
    private final TextView tvColor;

    public ArrowsLayout(Context context) {
        this(context, null);
    }

    public ArrowsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArrowsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //将一个布局界面转换成View对象
        View.inflate(context, R.layout.arrows_layout, this);
        tvTitle = (TextView) findViewById(R.id.tv_select);
        tvColor = (TextView) findViewById(R.id.tv_color);
    }

    public void setTvTitle(String content){
        tvTitle.setText(content);
    }
    public void setTvColor(String content){
        tvColor.setText(content);
    }
}
