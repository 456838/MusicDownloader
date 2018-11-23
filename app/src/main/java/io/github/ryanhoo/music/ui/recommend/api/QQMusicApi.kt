package io.github.ryanhoo.music.ui.recommend.api

import com.salton123.config.RetrofitManager
import io.github.ryanhoo.music.data.model.HotSongList
import io.github.ryanhoo.music.data.model.SongList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * User: newSalton@outlook.com
 * Date: 2018/11/22 7:24 PM
 * ModifyTime: 7:24 PM
 * Description:
 */
interface QQMusicApi {

    companion object {
        val BASE_URL = "https://api.bzqll.com/music/tencent/"

        fun getQQMusicService() = RetrofitManager.getRetrofit(QQMusicApi.BASE_URL).create(QQMusicApi::class.java)

    }

    /**
     * 热门歌曲
     * categoryId 分类ID，通过上面的分类接口获取 默认获取全部
     * sortId 排序ID 1 默认 2 最新 3 热门 4 评分 默认按照热门排序
     * limit 获取数量 默认60 最大值60
     * https://api.bzqll.com/music/tencent/hotSongList?key=579621905&categoryId=10000000&sortId=3&limit=60
     */
    @GET("hotSongList?key=579621905")
    fun hotSongList(
        @Query("categoryId") categoryId: String,
        @Query("sortId") sortId: String,
        @Query("limit") limit: String
    ): Observable<HotSongList>

    /**
     * 歌单获取
     * id 	√ 	歌单的ID 	无
     * https://api.bzqll.com/music/tencent/songList?key=579621905&id=1147906982
     */
    @GET("songList?key=579621905")
    fun songList(
        @Query("id") songId: String
    ): Observable<SongList>
}