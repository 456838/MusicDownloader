package com.salton123.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * User: newSalton@outlook.com
 * Date: 2019/3/19 10:01
 * ModifyTime: 10:01
 * Description:
 */
public interface IComponentLife extends View.OnClickListener {

    int getLayout();    //当前布局

    View getRootView();

    View getTitleBar();

    Activity activity();

    LayoutInflater inflater();

    <T extends View> T f(int resId);

    void initVariable(Bundle savedInstanceState);    //在setContentView之前初始化数据

    void initViewAndData();  //初始化数据在View之后

    void initListener();     //监听回调

    void longToast(String toast);    //长Toast

    void shortToast(String toast);   //短Toast

    void log(String msg);

    void openActivity(Class<?> clz, Bundle bundle);

    void openActivityForResult(Class<?> clz, Bundle bundle, int requestCode);

    void setListener(int... ids);

    void setListener(View... views);

    void show(View... views);

    void hide(View... views);
}
