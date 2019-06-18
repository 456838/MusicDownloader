package com.salton123.bmob.biz.music

import cn.bmob.v3.BmobObject

/**
 * User: newSalton@outlook.com
 * Date: 2019/6/18 18:11
 * ModifyTime: 18:11
 * Description:
 */
class MusicBean(
        var code: Int? = 200,
        var msg: String = "操作成功",

        //来源网站
        var site: String? = null,
        //歌曲地址
        var link: String? = null,
        //歌曲id
        var songid: String? = null,
        //歌名
        var title: String? = null,
        //演唱者
        var author: String? = null,
        //歌词
        var lrc: String? = null,
        //播放地址
        var url: String? = null,
        //封面
        var pic: String? = null,
        //专辑名
        var albumName: String? = null
) : BmobObject()
