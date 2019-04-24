package com.salton123.base.feature;


import android.app.Activity;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

/**
 * User: newSalton@outlook.com
 * Date: 2018/12/25 4:51 PM
 * ModifyTime: 4:51 PM
 * Description:
 */
public class ImmersionFeature implements IFeature {
    private ImmersionBar mImmersionBar;
    private Activity mActivity;

    public ImmersionFeature(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onBind() {
        mImmersionBar = getImmersionBar();
        mImmersionBar.init();
    }

    public ImmersionBar getImmersionBar() {
        if (mImmersionBar == null) {
            mImmersionBar = ImmersionBar.with(mActivity);
                    // .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                    // .transparentStatusBar()
                    // .transparentBar()
                    // .transparentNavigationBar();
        }
        return mImmersionBar;
    }

    @Override
    public void onUnBind() {
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    public void dardFont() {
        getImmersionBar().statusBarDarkFont(true).init();
    }

    public void lightFont() {
        getImmersionBar().statusBarDarkFont(false).init();
    }
}
