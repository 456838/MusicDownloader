package com.salton123.musicdownloader;

import android.app.Application;

import org.xutils.x;


/**
 * Created by shuji on 16/11/6.
 */

public class SaltonApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }


}
