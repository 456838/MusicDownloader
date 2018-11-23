package com.salton123.xmly.business

import com.salton123.mvp.presenter.BasePresenter
import com.salton123.mvp.view.BaseView

/**
 * User: newSalton@outlook.com
 * Date: 2018/5/23 下午6:28
 * ModifyTime: 下午6:28
 * Description:
 */
interface RequestContract {

    interface IRequestPresenter : BasePresenter<IRequestView> {
        fun getHotSongList()
        fun getSongList(songId: Int)
    }

    interface IRequestView : BaseView {
        fun onError(code: Int, msg: String)

        fun <T> onSucceed(data: T?)

    }
}
