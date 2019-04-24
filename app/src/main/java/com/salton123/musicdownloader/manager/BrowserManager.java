package com.salton123.musicdownloader.manager;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/24 15:16
 * ModifyTime: 15:16
 * Description:
 */
public enum BrowserManager {
    INSTANCE;
    private List<BrowserEntity> mBrowserFragments = new ArrayList<>();

    public void clear() {
        mBrowserFragments.clear();
    }

    public void add(BrowserEntity entity) {
        mBrowserFragments.add(entity);
    }

    public BrowserEntity find(Fragment fragment) {
        for (BrowserEntity item : mBrowserFragments) {
            if (item.fragment == fragment) {
                return item;
            }
        }
        return null;
    }

    public void update(BrowserEntity entity) {
        for (BrowserEntity item : mBrowserFragments) {
            if (item.fragment == entity.fragment) {
                item.previewPicPath = entity.previewPicPath;
            }
        }
    }

    public List<BrowserEntity> getData() {
        return mBrowserFragments;
    }

}
