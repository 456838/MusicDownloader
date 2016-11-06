package com.salton123.musicdownloader;

import android.app.Application;

import java.io.File;

import cn.finalteam.filedownloaderfinal.DownloaderManager;
import cn.finalteam.filedownloaderfinal.DownloaderManagerConfiguration;
import cn.finalteam.toolsfinal.StorageUtils;

/**
 * Created by shuji on 16/11/6.
 */

public class SaltonApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initDownloaderManager();
    }

    private void initDownloaderManager() {
        //下载文件所保存的目录
        File storeFile = StorageUtils.getCacheDirectory(this, false, "FileDownloader");
        if (!storeFile.exists()) {
            storeFile.mkdirs();
        }

        final DownloaderManagerConfiguration.Builder dmBulder = new DownloaderManagerConfiguration.Builder(this)
                .setMaxDownloadingCount(3) //配置最大并行下载任务数，配置范围[1-100]
                .setDbExtField(null) //配置数据库扩展字段
        .setDbVersion(1)//配置数据库版本
        .setDbUpgradeListener(null) //配置数据库更新回调
        .setDownloadStorePath(storeFile.getAbsolutePath()); //配置下载文件存储目录

        //初始化下载管理最好放到线程中去执行防止卡顿情况
        new Thread() {
            @Override
            public void run() {
                super.run();
                DownloaderManager.getInstance().init(dmBulder.build());//必要语句
            }
        }.start();
    }
}
