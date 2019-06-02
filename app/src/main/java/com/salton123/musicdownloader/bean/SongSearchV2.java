package com.salton123.musicdownloader.bean;

import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2019/5/20 21:21
 * ModifyTime: 21:21
 * Description:
 */
public class SongSearchV2 {
    public int status;
    public int error_code;
    public DataBean data;

    public static class DataBean {
        public int page;
        public int total;
        public int pagesize;
        public List<ResultItem> lists;
    }

    public static class ResultItem {
        public String SongName;
        public int Bitrate;
        public String OriSongName;
        public int Audioid;
        public String FileName;
        public String FileHash;
        public String SingerName;
        public int FileSize;
        public String AlbumID;
        public String ID;
    }
}
