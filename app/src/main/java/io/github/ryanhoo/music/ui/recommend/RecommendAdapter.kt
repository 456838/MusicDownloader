package io.github.ryanhoo.music.ui.recommend

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.hazz.kotlinmvp.view.recyclerview.MultipleType
import com.hazz.kotlinmvp.view.recyclerview.ViewHolder
import com.salton123.xmly.business.RequestContract
import io.github.ryanhoo.music.R
import io.github.ryanhoo.music.data.model.HotSongList
import io.github.ryanhoo.music.ui.recommend.MultiTypeItem.TYPE_HOT_SONG
import io.github.ryanhoo.music.ui.recommend.MultiTypeItem.TYPE_GUESS_LIKE
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
                        val layoutManager = LinearLayoutManager(context as Activity, LinearLayoutManager.HORIZONTAL, false)
                        layoutManager.initialPrefetchItemCount = 3
                        val hotSongAdapter = HotSongAdapter(context)
                        hotSongAdapter.addAll(target.data.toMutableList())
                        it.adapter = hotSongAdapter
                        it.recycledViewPool = recyclerViewPool
                    }
                }
            }
            TYPE_GUESS_LIKE -> {
//                if (data.item is GussLikeAlbumList) {
//                    val gussLikeAlbumList = data.item as GussLikeAlbumList
//                    gussLikeAlbumList.albumList.shuffle()
//                    holder.setText(R.id.tv_title, "猜你喜欢")
//                    holder.getView<RecyclerView>(R.id.fl_recyclerView).let {
//                        val layoutManager = LinearLayoutManager(context as Activity, LinearLayoutManager.HORIZONTAL, false)
//                        layoutManager.initialPrefetchItemCount = 3
//                        it.layoutManager = layoutManager
//                        it.recycledViewPool = recyclerViewPool
//                        it.adapter = GuessLikeTypeHorizontalAdapter(context, gussLikeAlbumList.albumList.subList(0, 10), R.layout.xmly_item_play_type_guess_like_stub)
//                    }
//                    holder.getView<TextView>(R.id.tv_more).let {
//                        it.setOnClickListener {
//                            Toast.makeText(context, "换一批", Toast.LENGTH_LONG).show()
//                            presenter.getGuessLikeAlbum("50")
//                        }
//                    }
//                }
            }
            TYPE_RECOMMEND_ALBUMS -> {
//                if (data.item is DiscoveryRecommendAlbums) {
//                    val discoveryRecommendAlbum = data.item as DiscoveryRecommendAlbums
//                    holder.setText(R.id.tv_title, discoveryRecommendAlbum.displayCategoryName)
//                    holder.getView<RecyclerView>(R.id.fl_recyclerView).let {
//                        it.layoutManager = LinearLayoutManager(context as Activity, LinearLayoutManager.HORIZONTAL, false)
//                        it.adapter = DiscoveryRecommendAlbumsAdapter(context, discoveryRecommendAlbum.albumList, R.layout.xmly_item_play_type_recommend_albums_stub)
//                        it.recycledViewPool = recyclerViewPool
//                    }
//                    holder.getView<TextView>(R.id.tv_more).let {
//                        it.setOnClickListener { Toast.makeText(context, "更多", Toast.LENGTH_LONG).show() }
//                    }
//                }

            }
        }
    }
}