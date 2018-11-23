package io.github.ryanhoo.music.ui.recommend

import android.app.Activity
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.hazz.kotlinmvp.view.recyclerview.MultipleType
import com.hazz.kotlinmvp.view.recyclerview.ViewHolder
import com.hazz.kotlinmvp.view.recyclerview.adapter.OnItemClickListener
import com.hwangjr.rxbus.RxBus
import com.salton123.xmly.business.RequestContract
import io.github.ryanhoo.music.R
import io.github.ryanhoo.music.data.model.HotSong
import io.github.ryanhoo.music.data.model.HotSongList
import io.github.ryanhoo.music.event.EventTags.FRAGMENT_DELEGATE
import io.github.ryanhoo.music.ui.recommend.MultiTypeItem.TYPE_GUESS_LIKE
import io.github.ryanhoo.music.ui.recommend.MultiTypeItem.TYPE_HOT_SONG
import io.github.ryanhoo.music.ui.recommend.MultiTypeItem.TYPE_RECOMMEND_ALBUMS

/**
 * User: newSalton@outlook.com
 * Date: 2018/11/22 6:46 PM
 * ModifyTime: 6:46 PM
 * Description:
 */
class RecommendAdapter(context: Context, var presenter: RequestContract.IRequestPresenter)
    : XRefreshRecyclerAdapter<MultiTypeItem>(context, object : MultipleType<MultiTypeItem> {

    override fun getLayoutId(item: MultiTypeItem, position: Int): Int {
        return when (item.viewType) {
            TYPE_HOT_SONG -> R.layout.adapter_item_hot_songs
//            TYPE_GUESS_LIKE -> R.layout.xmly_item_play_type_guess_like
//            TYPE_RECOMMEND_ALBUMS -> R.layout.xmly_item_play_type_recommend_albums
            else -> 0
        }
    }
}) {

    private val TAG = "RecommendAdapter"
    var recyclerViewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()
    override fun bindData(holder: ViewHolder, data: MultiTypeItem, position: Int) {
        val target = data.item
        when (data.viewType) {
            TYPE_HOT_SONG -> {
                if (target is HotSongList) {
                    holder.getView<RecyclerView>(R.id.hotRecyclerView).let {
                        val layoutManager = GridLayoutManager(context as Activity, 3)
                        layoutManager.isAutoMeasureEnabled = true
                        layoutManager.initialPrefetchItemCount = 3
                        val hotSongAdapter = HotSongAdapter(context)
                        hotSongAdapter.addAll(target.data.toMutableList())
                        it.adapter = hotSongAdapter
                        it.layoutManager = layoutManager
                        it.recycledViewPool = recyclerViewPool
                        hotSongAdapter.setOnItemClickListener(object : OnItemClickListener {
                            override fun onItemClick(obj: Any?, position: Int) {
                                if (obj is HotSong) {
                                    RxBus.get().post(FRAGMENT_DELEGATE,obj)
                                }
                            }
                        })
                    }
                }
            }
            TYPE_GUESS_LIKE -> {
            }
            TYPE_RECOMMEND_ALBUMS -> {
            }
        }
    }
}