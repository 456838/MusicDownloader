package io.github.ryanhoo.music.ui.recommend

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.hazz.kotlinmvp.view.recyclerview.MultipleType
import com.hazz.kotlinmvp.view.recyclerview.ViewHolder
import com.salton123.xmly.business.RequestContract
import io.github.ryanhoo.music.R
import io.github.ryanhoo.music.ui.recommend.MultiTypeItem.TYPE_BANNER
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
            TYPE_BANNER -> R.layout.adapter_item_play_type_banner
//            TYPE_GUESS_LIKE -> R.layout.xmly_item_play_type_guess_like
//            TYPE_RECOMMEND_ALBUMS -> R.layout.xmly_item_play_type_recommend_albums
            else -> 0
        }
    }
}) {

    private val TAG = "XmlyAdapter"
    var recyclerViewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()
    override fun bindData(holder: ViewHolder, data: MultiTypeItem, position: Int) {
        when (data.viewType) {
            TYPE_BANNER -> {
//                var banner = holder.getView<BGABanner>(R.id.banner)
//                banner.setAdapter(object : BGABanner.Adapter<ImageView, BannerV2> {
//                    override fun fillBannerItem(banner: BGABanner?, itemView: ImageView?, model: BannerV2?, position: Int) {
//                        itemView?.let {
//                            //                            itemView.scaleType = ImageView.ScaleType.CENTER_INSIDE
//                            GlideApp.with(context)
//                                .load(model?.bannerUrl)
//                                .placeholder(R.drawable.placeholder_banner)
//                                .thumbnail(0.5f)
//                                .dontAnimate()
//                                .transition(DrawableTransitionOptions().crossFade())
//                                .centerInside()
//                                .into(itemView)
//                        }
//                    }
//                })
//                banner.setDelegate(object : BGABanner.Delegate<ImageView, BannerV2> {
//                    override fun onBannerItemClick(banner: BGABanner?, itemView: ImageView?, model: BannerV2?, position: Int) {
////                        model?.bannerUrl?.let { Toast.makeText(context, "${model.bannerUrl}", Toast.LENGTH_LONG).show() }
//                    }
//                })
//                if (data.item is BannerV2List) {
//                    val bannerV2 = data.item as BannerV2List
////                    banner.setData(bannerV2.bannerV2s, bannerV2.bannerV2s.map { it.kind })
//                    banner.setData(bannerV2.bannerV2s, null)
//                }
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