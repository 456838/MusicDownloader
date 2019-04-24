package com.salton123.musicdownloader;

import android.os.Bundle;

import com.gyf.barlibrary.BarHide;
import com.salton123.base.BaseActivity;
import com.salton123.base.feature.ImmersionFeature;

/**
 * User: newSalton@outlook.com
 * Date: 2019/3/20 15:57
 * ModifyTime: 15:57
 * Description:
 */
public abstract class BookBaseActivity extends BaseActivity {
    private ImmersionFeature mImmersionFeature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mImmersionFeature = new ImmersionFeature(this);
        mImmersionFeature.getImmersionBar().hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR);
        addFeature(mImmersionFeature);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mImmersionFeature.onBind();
    }
}
