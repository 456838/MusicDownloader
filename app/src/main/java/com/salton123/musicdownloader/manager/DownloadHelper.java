package com.salton123.musicdownloader.manager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.widget.Toast;

import com.salton123.app.BaseApplication;

import xyz.yhsj.kmusic.entity.Song;

/**
 * User: newSalton@outlook.com
 * Date: 2019/6/2 21:49
 * ModifyTime: 21:49
 * Description:
 */
public class DownloadHelper {
    public static void systemDownload(Song song) {
        final DownloadManager dManager = (DownloadManager) BaseApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(song.getUrl());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        // 设置下载路径和文件名
        request.setDestinationInExternalPublicDir("download", "" + song.getTitle() + "_" + song.getAuthor()+".mp3");
        request.setDescription("歌曲：" + song.getTitle() + " 歌手：" + song.getAuthor());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setMimeType("audio/*");
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        // 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        // 获取此次下载的ID
        final long refernece = dManager.enqueue(request);
        // 注册广播接收器，当下载完成时自动安装
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        BroadcastReceiver receiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (refernece == myDwonloadID) {
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    Uri downloadFileUri = dManager.getUriForDownloadedFile(refernece);
                    Toast.makeText(context, "下载完毕：" + downloadFileUri.getPath(), Toast.LENGTH_LONG).show();
                    // install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                    // install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // startActivity(install);
                }
            }
        };
        BaseApplication.getInstance().registerReceiver(receiver, filter);
    }
}
