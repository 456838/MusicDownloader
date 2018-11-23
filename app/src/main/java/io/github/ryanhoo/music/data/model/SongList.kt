package io.github.ryanhoo.music.data.model

import com.google.gson.annotations.SerializedName

/**
 * User: newSalton@outlook.com
 * Date: 2018/11/23 6:25 PM
 * ModifyTime: 6:25 PM
 * Description:
 */

data class SongList(
    @SerializedName("result") val result: String,
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: SongListData
)

data class SongListData(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("desc") val desc: String,
    @SerializedName("author") val author: String,
    @SerializedName("songnum") val songnum: String,
    @SerializedName("logo") val logo: String,
    @SerializedName("songs") val songs: List<SongListSong>
)

data class SongListSong(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("singer") val singer: String,
    @SerializedName("url") val url: String,
    @SerializedName("pic") val pic: String,
    @SerializedName("lrc") val lrc: String
)