package com.salton123.view.adapter;

import android.content.Context;

import com.salton123.adapter.abslistview.CommonAdapter;
import com.salton123.adapter.abslistview.ViewHolder;
import com.salton123.musicdownloader.R;
import com.salton123.musicdownloader.manager.BrowserEntity;

import org.xutils.x;

public class BrowserListAdapter extends CommonAdapter<BrowserEntity> {
    public BrowserListAdapter(Context context) {
        super(context, R.layout.adapter_item_browser_list);
    }

    @Override
    protected void convert(ViewHolder viewHolder, BrowserEntity item, int position) {
        x.image().bind(viewHolder.getView(R.id.ivThumbnail), item.previewPicPath);
    }
}
