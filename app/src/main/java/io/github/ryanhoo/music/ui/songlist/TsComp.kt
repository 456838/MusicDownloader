package io.github.ryanhoo.music.ui.songlist

import android.os.Bundle
import com.salton123.base.BaseSupportFragment
import io.github.ryanhoo.music.R

/**
 * User: newSalton@outlook.com
 * Date: 2018/11/23 7:11 PM
 * ModifyTime: 7:11 PM
 * Description:
 */
class TsComp : BaseSupportFragment() {
    override fun getLayout(): Int = R.layout.comp_ts

    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
    }

    override fun onBackPressedSupport(): Boolean {
        pop()
        return true
    }
}