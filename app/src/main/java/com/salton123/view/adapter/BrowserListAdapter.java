package com.salton123.view.adapter;

import android.content.Context;

import com.salton123.musicdownloader.R;
import com.salton123.musicdownloader.manager.BrowserEntity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.xutils.x;

public class BrowserListAdapter extends CommonAdapter<BrowserEntity> {
    public BrowserListAdapter(Context context) {
        super(context, R.layout.adapter_item_browser_list, null);
    }

    @Override
    protected void convert(ViewHolder viewHolder, BrowserEntity item, int position) {
        x.image().bind(viewHolder.getView(R.id.ivThumbnail), item.previewPicPath);
    }
}
