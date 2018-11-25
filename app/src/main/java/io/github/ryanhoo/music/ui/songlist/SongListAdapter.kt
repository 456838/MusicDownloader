package io.github.ryanhoo.music.ui.songlist

import android.content.Context
import com.hazz.kotlinmvp.view.recyclerview.ViewHolder
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
        holder.setText(R.id.tvName, data.name + " " + data.singer)
    }
}