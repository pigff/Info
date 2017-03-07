package com.info.lin.infoproject.data.api;

import com.info.lin.infoproject.data.net.ZhiBeforeDailyResponse;
import com.info.lin.infoproject.data.net.ZhiDailyResponse;
import com.info.lin.infoproject.data.net.ZhiDetailResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lin on 2017/3/7.
 */

public interface ZhiApi {

    /**
     * 获取最新文章列表
     * @return
     */
    @GET("news/latest")
    Observable<ZhiDailyResponse> getLatestDaily();

    /**
     * 获取以前的文章列表
     * @return
     */
    @GET("news/before/{date}")
    Observable<ZhiBeforeDailyResponse> getBeforeDaily(@Path("date") String date);

    /**
     * 获取相应文章内容
     * @param storyId
     * @return
     */
    @GET("news/{storyId}")
    Observable<ZhiDetailResponse> getStoryContent(@Path("storyId") int storyId);

}
