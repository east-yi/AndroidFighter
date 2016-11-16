package com.ycc.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 16-11-16.
 */
public class GetFocsTextView extends TextView{
    /**
     * new时调用
     * @param context
     */
    public GetFocsTextView(Context context) {
        super(context);
    }

    /**
     * 系统调用(带上下文+属性）
     * @param context
     * @param attrs
     */
    public GetFocsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 系统调用(带上下文+属性+布局文件中定义的样式文件构造方法)
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public GetFocsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
