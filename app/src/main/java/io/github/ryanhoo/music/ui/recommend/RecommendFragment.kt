package io.github.ryanhoo.music.ui.recommend

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.andview.refreshview.XRefreshView
import com.andview.refreshview.XRefreshViewFooter
import com.hwangjr.rxbus.RxBus
import com.salton123.log.XLog
import com.salton123.mvp.ui.BaseSupportPresenterFragment
import com.salton123.util.NetUtil
import com.salton123.xmly.business.RequestContract
import com.salton123.xmly.business.RequestPresenter
import io.github.ryanhoo.music.R
import io.github.ryanhoo.music.data.model.HotSongList
import io.github.ryanhoo.music.event.EventTags
import io.github.ryanhoo.music.event.SearchActEvent
import io.github.ryanhoo.music.ui.widget.XRefreshViewFooterStyle
import io.github.ryanhoo.music.ui.widget.XRefreshViewHeaderStyle
import kotlinx.android.synthetic.main.fragment_recommend.*

/**
 * User: newSalton@outlook.com
 * Date: 2018/11/22 2:50 PM
 * ModifyTime: 2:50 PM
 * Description:
 */
class RecommendFragment : BaseSupportPresenterFragment<RequestContract.IRequestPresenter>()
    , RequestContract.IRequestView, XRefreshView.XRefreshViewListener {

    override fun onHeaderMove(headerMovePercent: Double, offsetY: Int) {
    }

    override fun onRelease(direction: Float) {
    }

    override fun onLoadMore(isSilence: Boolean) {
        getData()
    }

    override fun onRefresh() {
    }

    override fun onRefresh(isPullDown: Boolean) {
        refreshLayout.setLoadComplete(false)
        mAdapter.clear()
        getData()
    }

    private val mAdapter by lazy { RecommendAdapter(_mActivity, mPresenter) }
    override fun getLayout(): Int {
        return R.layout.fragment_recommend
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        mPresenter = RequestPresenter()
    }

    override fun initViewAndData() {
        if (!NetUtil.isNetworkAvailable(_mActivity)) {
            multipleStatusView.showNoNetwork()
        }
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter
        multipleStatusView.setOnClickListener { getData() }
        refreshLayout.setPinnedTime(1500)
        refreshLayout.setMoveForHorizontal(true)
        refreshLayout.pullLoadEnable = true
        refreshLayout.setAutoLoadMore(false)
        refreshLayout.setCustomHeaderView(XRefreshViewHeaderStyle(_mActivity))
        refreshLayout.setCustomFooterView(XRefreshViewFooterStyle(_mActivity))
        mAdapter.customLoadMoreView = XRefreshViewFooter(context)
        refreshLayout.enableReleaseToLoadMore(true)
        refreshLayout.enableRecyclerViewPullUp(true)
        refreshLayout.enablePullUpWhenLoadCompleted(true)
        refreshLayout.setXRefreshViewListener(this)
        etSearch.setOnClickListener { RxBus.get().post(EventTags.FRAGMENT_DELEGATE, SearchActEvent()) }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        getData()
    }

    private fun getData() {
        mPresenter.getHotSongList()
    }

    val TAG = "RecommendComponent"

    override fun onError(code: Int, msg: String) {
        XLog.e(TAG, "code=$code,msg=$msg")
        refreshLayout.stopRefresh()
        showEmpty()
    }

    private fun showEmpty() {
        if (mAdapter.getData().isEmpty()) {
            multipleStatusView.showEmpty()
        }
    }

    override fun <T> onSucceed(data: T?) {
        if (multipleStatusView.viewStatus != 0) {
            multipleStatusView.showContent()
        }
        when (data) {
            is HotSongList -> {
                mAdapter.add(0, MultiTypeItem(MultiTypeItem.TYPE_HOT_SONG, data))
                mAdapter.notifyItemChanged(1)
            }
        }
        refreshLayout.stopRefresh()
        refreshLayout.setLoadComplete(true)
        showEmpty()
    }

}