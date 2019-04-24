package com.salton123.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;


import com.salton123.base.feature.IFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2019/2/16 19:01
 * ModifyTime: 19:01
 * Description:
 */
public abstract class BaseActivity extends Activity implements IComponentLife {
    private ActivityDelegate mActivityDelegate = new ActivityDelegate(this);
    private List<IFeature> mFeatures = new ArrayList<>();

    public void addFeature(IFeature feature) {
        this.mFeatures.add(feature);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mActivityDelegate.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        for (IFeature item : mFeatures) {
            item.onBind();
        }
        setContentView(mActivityDelegate.onCreateView());
        mActivityDelegate.onViewCreated();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (IFeature item : mFeatures) {
            item.onUnBind();
        }
    }

    @Override
    public View getRootView() {
        return mActivityDelegate.getRootView();
    }

    @Override
    public View getTitleBar() {
        return null;
    }

    @Override
    public Activity activity() {
        return mActivityDelegate.activity();
    }

    @Override
    public LayoutInflater inflater() {
        return mActivityDelegate.inflater();
    }

    @Override
    public <T extends View> T f(int resId) {
        return mActivityDelegate.f(resId);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void longToast(String toast) {
        mActivityDelegate.longToast(toast);
    }

    @Override
    public void shortToast(String toast) {
        mActivityDelegate.shortToast(toast);
    }

    @Override
    public void log(String msg) {
        mActivityDelegate.log(msg);
    }

    @Override
    public void openActivity(Class<?> clz, Bundle bundle) {
        mActivityDelegate.openActivity(clz, bundle);
    }

    @Override
    public void openActivityForResult(Class<?> clz, Bundle bundle, int requestCode) {
        mActivityDelegate.openActivityForResult(clz, bundle, requestCode);
    }

    @Override
    public void setListener(int... ids) {
        mActivityDelegate.setListener(ids);
    }

    @Override
    public void setListener(View... views) {
        mActivityDelegate.setListener(views);
    }

    @Override
    public void show(View... views) {
        mActivityDelegate.show(views);
    }

    @Override
    public void hide(View... views) {
        mActivityDelegate.hide(views);
    }

    @Override
    public void onClick(View v) {

    }

}
