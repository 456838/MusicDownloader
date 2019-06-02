package com.salton123.musicdownloader.manager;

import com.salton123.log.XLog;
import com.salton123.musicdownloader.bean.SongSearchV2;
import com.salton123.musicdownloader.bean.Tracker;
import com.salton123.utils.RequestUtil;

/**
 * User: newSalton@outlook.com
 * Date: 2019/5/19 17:47
 * ModifyTime: 17:47
 * Description: https://blog.csdn.net/qiuxy23/article/details/81266030
 */
public class RequestManager {
    private static final String SONG_SEARCH_V2 = "http://songsearch.kugou.com/song_search_v2?keyword=%keyword%&page=%page%&pagesize=%pagesize%";
    private static final String TRACKER_V2 = "http://trackercdn.kugou.com/i/v2/?cmd=25&key=%key%&hash=%hash%&behavior=download";

    /**
     * keyword search song
     */
    public void songSearch(String keyword, int page, int pageSize, RequestUtil.HttpResponseCallback callback) {
        String finalSearchUrl = SONG_SEARCH_V2
                .replace("%keyword%", keyword)
                .replace("%page%", page + "")
                .replace("%pagesize%", pageSize + "");
        XLog.i(this, "finalSearchUrl=" + finalSearchUrl);
        RequestUtil.get(finalSearchUrl, SongSearchV2.class, callback);
    }

    public void tracker(String key, RequestUtil.HttpResponseCallback callback) {
        String finalSearchUrl = TRACKER_V2.replace("%key%", key);
        XLog.i(this, "finalSearchUrl=" + finalSearchUrl);
        RequestUtil.get(finalSearchUrl, Tracker.class, callback);

    }
}
