package com.info.lin.infoproject.data.api;

import com.info.lin.infoproject.data.net.DayHistoryResponse;
import com.info.lin.infoproject.data.net.GankBeautyResponse;
import com.info.lin.infoproject.data.net.GankDailyResponse;
import com.info.lin.infoproject.data.net.GankDataResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lin on 2017/2/18.
 */
public interface GankApi {

    @GET("data/福利/{number}/{page}")
    Observable<GankBeautyResponse> getBeauties(@Path("number") int number, @Path("page") int page);

    @GET("day/history")
    Observable<DayHistoryResponse> getDayHistory();

    @GET("day/{year}/{month}/{day}")
    Observable<GankDailyResponse> getDailyData(
            @Path("year") int year, @Path("month") int month, @Path("day") int day);

    @GET("data/{category}/{pageCount}/{page}")
    Observable<GankDataResponse> getGankData(@Path("category") String category, @Path("pageCount") int pageCount, @Path("page") int page);
}
