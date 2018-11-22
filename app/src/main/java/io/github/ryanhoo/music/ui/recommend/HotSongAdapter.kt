package io.github.ryanhoo.music.ui.recommend

import android.content.Context
import android.widget.ImageView
import com.hazz.kotlinmvp.view.recyclerview.ViewHolder
import com.salton123.GlideApp
import com.salton123.base.recyclerview.adapter.CommonAdapter
import io.github.ryanhoo.music.R
import io.github.ryanhoo.music.data.model.HotSong

/**
 * User: newSalton@outlook.com
 * Date: 2018/11/22 8:10 PM
 * ModifyTime: 8:10 PM
 * Description:
 */

class HotSongAdapter(context: Context) : CommonAdapter<HotSong>(context, R.layout.adapter_item_hot_songs_stub_shortcut) {
    override fun bindData(holder: ViewHolder, data: HotSong, position: Int) {
        holder.setImagePath(R.id.ivThumbnail, object : ViewHolder.HolderImageLoader(data.pic) {
            override fun loadImage(iv: ImageView, path: String) {
                GlideApp.with(iv).load(path).into(iv)
            }
        })
        holder.setText(R.id.tvCreateTime, data.createTime)
            .setText(R.id.tvCreator, data.creator)
            .setText(R.id.tvName, data.name)

    }

}