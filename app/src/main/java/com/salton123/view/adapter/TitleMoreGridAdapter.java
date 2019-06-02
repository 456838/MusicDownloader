package com.salton123.view.adapter;

import android.content.Context;

import com.salton123.musicdownloader.R;
import com.salton123.musicdownloader.bean.GridMenuItem;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

public class TitleMoreGridAdapter extends CommonAdapter<GridMenuItem> {
    public TitleMoreGridAdapter(Context context) {
        super(context, R.layout.adapter_item_title_more_grid, null);
    }

    @Override
    protected void convert(ViewHolder viewHolder, GridMenuItem item, int position) {
        viewHolder.setText(R.id.tvAction, item.name).setText(R.id.tvIcon, item.icon);
    }
}
