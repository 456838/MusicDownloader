package com.salton123.bmob

import android.content.Context
import cn.bmob.sdkdemo.BuildConfig
import cn.bmob.v3.Bmob


/**
 * User: newSalton@outlook.com
 * Date: 2019/6/1 16:18
 * ModifyTime: 16:18
 * Description:
 */
object BmobInitialier {
    fun init(context: Context) {
        Bmob.initialize(context, BuildConfig.BMOB_KEY)
    }
}