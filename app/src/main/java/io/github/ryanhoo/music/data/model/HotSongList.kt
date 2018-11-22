package io.github.ryanhoo.music.data.model

/**
 * User: newSalton@outlook.com
 * Date: 2018/11/22 6:22 PM
 * ModifyTime: 6:22 PM
 * Description:
 */

data class HotSongList(
    val result: String,
    val code: Int,
    val data: List<Data>
)

data class Data(
    val id: String,
    val name: String,
    val creator: String,
    val createTime: String,
    val pic: String,
    val playCount: String
)