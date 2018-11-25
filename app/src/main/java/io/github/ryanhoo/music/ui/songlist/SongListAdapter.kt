package io.github.ryanhoo.music.ui.songlist

import android.content.Context
import android.widget.ImageView
import com.hazz.kotlinmvp.view.recyclerview.ViewHolder
import com.salton123.GlideApp
import com.salton123.base.recyclerview.adapter.CommonAdapter
import io.github.ryanhoo.music.R
import io.github.ryanhoo.music.data.model.SongListSong

/**
 * User: newSalton@outlook.com
 * Date: 2018/11/25 下午1:58
 * ModifyTime: 下午1:58
 * Description:
 */
class SongListAdapter(context: Context)
    : CommonAdapter<SongListSong>(context, R.layout.adapter_item_song_list) {
    override fun bindData(holder: ViewHolder, data: SongListSong, position: Int) {
        holder.setText(R.id.tvName, data.name)
            .setText(R.id.tvInfo, data.singer)
        holder.setImagePath(R.id.ivThumbnail, object : ViewHolder.HolderImageLoader(data.pic) {
            override fun loadImage(iv: ImageView, path: String) {
                GlideApp.with(iv).load(path).circleCrop().thumbnail(0.7f).into(iv)
            }
        })
    }
}