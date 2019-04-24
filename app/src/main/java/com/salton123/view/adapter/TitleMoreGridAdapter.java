package com.salton123.view.adapter;

import android.content.Context;

import com.salton123.adapter.abslistview.CommonAdapter;
import com.salton123.adapter.abslistview.ViewHolder;
import com.salton123.musicdownloader.R;
import com.salton123.musicdownloader.bean.GridMenuItem;

public class TitleMoreGridAdapter extends CommonAdapter<GridMenuItem> {
    public TitleMoreGridAdapter(Context context) {
        super(context, R.layout.adapter_item_title_more_grid);
    }

    @Override
    protected void convert(ViewHolder viewHolder, GridMenuItem item, int position) {
        viewHolder.setText(R.id.tvAction, item.name).setText(R.id.tvIcon, item.icon);
    }
}
