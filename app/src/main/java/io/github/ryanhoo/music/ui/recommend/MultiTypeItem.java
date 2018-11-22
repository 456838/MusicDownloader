package io.github.ryanhoo.music.ui.recommend;

import android.support.annotation.NonNull;


/**
 * User: newSalton@outlook.com
 * Date: 2017/7/28 20:57
 * ModifyTime: 20:57
 * Description:
 */
public class MultiTypeItem implements Comparable<MultiTypeItem> {

    // 首页
    public static final int TYPE_BANNER = 0; //banner推荐
    public static final int TYPE_GUESS_LIKE = 1;     //猜你喜欢
    public static final int TYPE_RECOMMEND_ALBUMS = 2;     //推荐相册

    private int viewType;
    private Object item;

    public MultiTypeItem(int viewType, Object item) {
        this.viewType = viewType;
        this.item = item;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }


    @Override
    public String toString() {
        return "MultiTypeItem{" +
                "viewType=" + viewType +
                ", item=" + item +
                '}';
    }

    @Override
    public int compareTo(@NonNull MultiTypeItem another) {
        if (another.viewType > viewType) {
            return -1;
        } else if (another.viewType < viewType) {
            return 1;
        } else {
            return 0;
        }
    }
}
