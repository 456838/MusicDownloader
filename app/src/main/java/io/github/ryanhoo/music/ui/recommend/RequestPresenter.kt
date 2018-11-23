package com.salton123.xmly.business

import android.annotation.SuppressLint
import com.salton123.mvp.presenter.RxPresenter
import com.salton123.util.RxUtils
import io.github.ryanhoo.music.ui.recommend.api.QQMusicApi.Companion.getQQMusicService

/**
 * User: newSalton@outlook.com
 * Date: 2018/5/23 下午6:36
 * ModifyTime: 下午6:36
 * Description:
 */
class RequestPresenter : RxPresenter<RequestContract.IRequestView>(), RequestContract.IRequestPresenter {

    @SuppressLint("CheckResult")
    override fun getSongList(songId: Int) {
        getQQMusicService()
            .songList("$songId ")
            .compose(RxUtils.rxSchedulerHelper())
            .subscribe({
                mView?.onSucceed(it)
            }, {
                mView?.onError(-2, it.localizedMessage)
            })
    }

    @SuppressLint("CheckResult")
    override fun getHotSongList() {
        getQQMusicService()
            .hotSongList("10000000", "3", "60")
            .compose(RxUtils.rxSchedulerHelper())
            .subscribe({
                mView?.onSucceed(it)
            }, {
                mView?.onError(-1, it.localizedMessage)
            })
    }

}
