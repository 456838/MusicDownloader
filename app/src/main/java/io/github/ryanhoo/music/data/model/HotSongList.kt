package io.github.ryanhoo.music.data.model

import com.google.gson.annotations.SerializedName
import io.github.ryanhoo.music.event.IFragmentDelegateEvent

/**
 * User: newSalton@outlook.com
 * Date: 2018/11/22 6:22 PM
 * ModifyTime: 6:22 PM
 * Description:
 */

data class HotSongList(
    @SerializedName("result") val result: String,
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: List<HotSong>
)

data class HotSong(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("creator") val creator: String,
    @SerializedName("createTime") val createTime: String,
    @SerializedName("pic") val pic: String,
    @SerializedName("playCount") val playCount: String)
    : IFragmentDelegateEvent