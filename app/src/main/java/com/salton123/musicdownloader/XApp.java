package com.salton123.musicdownloader;

import com.salton123.app.BaseApplication;

import org.xutils.x;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/24 16:11
 * ModifyTime: 16:11
 * Description:
 */
public class XApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
