package com.salton123.ui.biz;

import android.os.Bundle;
import android.view.View;

import com.gyf.barlibrary.BarHide;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.salton123.corelite.R;
import com.salton123.feature.ImmersionFeature;
import com.salton123.ui.base.BaseActivity;

/**
 * User: newSalton@outlook.com
 * Date: 2019/6/19 10:52
 * ModifyTime: 10:52
 * Description:
 */
public abstract class BaseTitleActivity extends BaseActivity implements OnTitleBarListener {
    TitleBar titleBar;

    private ImmersionFeature mImmersionFeature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mImmersionFeature = new ImmersionFeature(this);
        mImmersionFeature.getImmersionBar()
                // .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .statusBarDarkFont(true)
        .statusBarColor(android.R.color.white);
        addFeature(mImmersionFeature);
        super.onCreate(savedInstanceState);
    }

    // @Override
    // public void onWindowFocusChanged(boolean hasFocus) {
    //     super.onWindowFocusChanged(hasFocus);
    //     mImmersionFeature.onBind();
    // }

    @Override
    public View getTitleBar() {
        if (titleBar == null) {
            titleBar = new TitleBar(activity());
            titleBar.setLeftIcon(R.drawable.ic_left);
            titleBar.setLeftTitle("返回");
            titleBar.setOnTitleBarListener(this);
        }
        return titleBar;
    }

    @Override
    public void onLeftClick(View v) {
        finish();
    }

    @Override
    public void onTitleClick(View v) {

    }

    @Override
    public void onRightClick(View v) {

    }
}
