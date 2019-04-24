package com.salton123.musicdownloader.bean;

public class GridBookmarkItem {
    public String title;
    public String subTitle;
    public String url;
    public int backgroundColor;

    public GridBookmarkItem(String title, String subTitle, String url, int backgroundColor) {
        this.title = title;
        this.subTitle = subTitle;
        this.url = url;
        this.backgroundColor = backgroundColor;
    }
}
