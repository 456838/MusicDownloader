package com.salton123.musicdownloader.view.adapter;

import com.salton123.GlideApp;
import com.salton123.musicdownloader.R;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import xyz.yhsj.kmusic.entity.Song;

/**
 * User: newSalton@outlook.com
 * Date: 2019/6/2 20:57
 * ModifyTime: 20:57
 * Description:
 */
public class SearchResultAdapter extends BGARecyclerViewAdapter<Song> {
    public SearchResultAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_item_bookmark_grid);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, Song model) {
        helper.setText(R.id.tvTitle, model.getTitle())
                .setText(R.id.tvSubTitle, model.getAuthor())
                .setText(R.id.tvOrder, position + 1 + "");
        GlideApp.with(helper.getConvertView())
                .load(model.getPic())
                .placeholder(R.drawable.salton_load_pic)
                .circleCrop()
                .into(helper.getImageView(R.id.ivIcon));

    }
}
