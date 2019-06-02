package com.salton123.view.adapter;

import android.content.Context;
import android.content.res.ColorStateList;

import com.salton123.musicdownloader.R;
import com.salton123.musicdownloader.bean.GridBookmarkItem;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

public class BookmarkGridAdapter extends CommonAdapter<GridBookmarkItem> {
    public BookmarkGridAdapter(Context context) {
        super(context, R.layout.adapter_item_bookmark_grid, null);
    }

    @Override
    protected void convert(ViewHolder viewHolder, GridBookmarkItem item, int position) {

        viewHolder.setText(R.id.tvTitle, item.title).setText(R.id.tvSubTitle, item.subTitle);
        int[] mColors = new int[]{item.backgroundColor, item.backgroundColor};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_enabled};
        ColorStateList colorStateList = new ColorStateList(states, mColors);
        viewHolder.getView(R.id.llRoot).setBackgroundTintList(colorStateList);
    }
}
