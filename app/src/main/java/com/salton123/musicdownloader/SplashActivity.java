package com.salton123.musicdownloader;

import android.os.Bundle;
import android.view.View;

import com.salton123.base.feature.PermissionFeature;
import com.salton123.musicdownloader.manager.BrowserEntity;
import com.salton123.musicdownloader.manager.BrowserManager;
import com.salton123.musicdownloader.ui.fm.BrowserFragment;
import com.salton123.musicdownloader.ui.fm.BrowserListPopupComp;
import com.salton123.musicdownloader.ui.fm.MenuPopupComp;
import com.salton123.musicdownloader.ui.fm.TitleMorePopupComp;
import com.salton123.utils.FragmentUtil;


/**
 * User: newSalton@outlook.com
 * Date: 2019/2/16 18:18
 * ModifyTime: 18:18
 * Description:
 */
public class SplashActivity extends BookBaseActivity {

    private BrowserFragment mCurrentBrowserFragment;

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public View getTitleBar() {
        return inflater().inflate(R.layout.default_search_title, null);
    }

    @Override
    public void initVariable(Bundle savedInstanceState) {
        addFeature(new PermissionFeature(this));
    }

    @Override
    public void initViewAndData() {
        setListener(R.id.tvTitleMore);
        addBrowserInstance();
    }

    private void addBrowserInstance() {
        BrowserEntity entity = BrowserEntity.newInstance();
        mCurrentBrowserFragment = entity.fragment;
        BrowserManager.INSTANCE.add(entity);
        FragmentUtil.add(getFragmentManager(), entity.fragment, R.id.flContainer, entity.tag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tvTitleMore:
                new TitleMorePopupComp().show(getFragmentManager(), "TitleMorePopupWindow");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BrowserManager.INSTANCE.clear();
    }
}
