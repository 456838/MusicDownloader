package com.salton123.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.salton123.musicdownloader.R;

/**
 * User: newSalton@outlook.com
 * Date: 2019/2/17 9:25 AM
 * ModifyTime: 9:25 AM
 * Description:
 */
public class NvUrlView extends LinearLayout {
    public NvUrlView(Context context) {
        this(context, null);
    }

    public NvUrlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NvUrlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.search_title_inner, this);
    }
}
