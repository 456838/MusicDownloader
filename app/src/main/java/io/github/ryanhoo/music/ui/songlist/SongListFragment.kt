package io.github.ryanhoo.music.ui.songlist

import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.andview.refreshview.XRefreshView
import com.andview.refreshview.XRefreshViewFooter
import com.hazz.kotlinmvp.view.recyclerview.adapter.OnItemClickListener
import com.hazz.kotlinmvp.view.recyclerview.adapter.OnItemLongClickListener
import com.liulishuo.okdownload.DownloadListener
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed
import com.salton123.base.FragmentDelegate
import com.salton123.log.XLog
import com.salton123.mvp.ui.BaseSupportPresenterFragment
import com.salton123.util.NetUtil
import com.salton123.xmly.business.RequestContract
import com.salton123.xmly.business.RequestPresenter
import io.github.ryanhoo.music.R
import io.github.ryanhoo.music.data.model.HotSongList
import io.github.ryanhoo.music.data.model.SongList
import io.github.ryanhoo.music.data.model.SongListSong
import io.github.ryanhoo.music.ui.recommend.MultiTypeItem
import io.github.ryanhoo.music.ui.recommend.RecommendAdapter
import kotlinx.android.synthetic.main.fragment_recommend.*
import java.io.File
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.cause.ResumeFailedCause
import com.liulishuo.okdownload.SpeedCalculator
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo
import com.liulishuo.okdownload.core.listener.DownloadListener2
import java.util.concurrent.atomic.AtomicLong


/**
 * User: newSalton@outlook.com
 * Date: 2018/11/23 6:36 PM
 * ModifyTime: 6:36 PM
 * Description:
 */
class SongListFragment : BaseSupportPresenterFragment<RequestContract.IRequestPresenter>()
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

    private val mAdapter by lazy { SongListAdapter(_mActivity) }
    override fun getLayout(): Int {
        return R.layout.fragment_song_list
    }

    private var songListId = ""
    override fun initVariable(savedInstanceState: Bundle?) {
        mPresenter = RequestPresenter()
        songListId = arguments.getString(FragmentDelegate.ARG_ITEM)
    }

    override fun initViewAndData() {
        if (!NetUtil.isNetworkAvailable(_mActivity)) {
            multipleStatusView.showNoNetwork()
        }
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter
        multipleStatusView.setOnClickListener { getData() }
        refreshLayout.setPinnedTime(1000)
        refreshLayout.setMoveForHorizontal(true)
        refreshLayout.pullLoadEnable = true
        refreshLayout.setAutoLoadMore(false)
        refreshLayout.enableReleaseToLoadMore(true)
        refreshLayout.enableRecyclerViewPullUp(true)
        refreshLayout.enablePullUpWhenLoadCompleted(true)
        refreshLayout.setXRefreshViewListener(this)
        mAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(obj: Any?, position: Int) {
                if (obj is SongListSong) {
                    longToast(obj.url)
                    DownloadTask.Builder(obj.url, File("/sdcard/music/${obj.name}-${obj.singer}.mp3")).build()
                        .enqueue(SampleListener())
                }
            }
        })
    }

    private open inner class SampleListener : DownloadListener2() {
        override fun taskStart(@NonNull task: DownloadTask) {
            print("task start")
        }

        override fun taskEnd(@NonNull task: DownloadTask, @NonNull cause: EndCause,
                             @Nullable realCause: Exception?) {
            longToast("下载完成")
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        getData()
    }

    private fun getData() {
        mPresenter.getSongList(songListId)
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
            is SongList -> {
                mAdapter.addAll(data.data.songs.toMutableList())
                mAdapter.notifyDataSetChanged()
            }
        }
        refreshLayout.stopRefresh()
        refreshLayout.setLoadComplete(true)
        showEmpty()
    }

    override fun onBackPressedSupport(): Boolean {
        pop()
        return true
    }
}