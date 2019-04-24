package com.salton123.musicdownloader.manager;

import com.salton123.musicdownloader.ui.fm.SearchResultFragment;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/24 15:10
 * ModifyTime: 15:10
 * Description:
 */
public class BrowserEntity {
    public SearchResultFragment fragment;
    public String tag;
    public String previewPicPath;

    public static BrowserEntity newInstance() {
        BrowserEntity entity = new BrowserEntity();
        entity.fragment = new SearchResultFragment();
        entity.tag = "BrowserFragment_" + System.currentTimeMillis();
        return entity;
    }
}
