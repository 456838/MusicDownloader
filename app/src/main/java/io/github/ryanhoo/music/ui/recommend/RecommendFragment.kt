package io.github.ryanhoo.music.ui.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.ryanhoo.music.R
import io.github.ryanhoo.music.ui.base.BaseFragment

/**
 * User: newSalton@outlook.com
 * Date: 2018/11/22 2:50 PM
 * ModifyTime: 2:50 PM
 * Description:
 */
class RecommendFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_play_list, container, false)
    }
}