package com.salton123.musicdownloader;

import android.os.Debug;

import com.salton123.app.BaseApplication;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/24 16:11
 * ModifyTime: 16:11
 * Description:
 */
public class XApp extends BaseApplication {
    @Override
    public void onCreate() {
        Debug.startMethodTracing("/sdcard/183/debug.trace");
        super.onCreate();
    }
}
