package com.info.lin.infoproject.network;

import android.text.TextUtils;

import com.info.lin.infoproject.data.api.GankApi;
import com.info.lin.infoproject.data.api.ZhiApi;
import com.info.lin.infoproject.data.net.DailyDataBean;
import com.info.lin.infoproject.data.net.DailyStory;
import com.info.lin.infoproject.data.net.DayHistoryResponse;
import com.info.lin.infoproject.data.net.GankBeautyResponse;
import com.info.lin.infoproject.data.net.GankDailyResponse;
import com.info.lin.infoproject.data.net.GankDataResponse;
import com.info.lin.infoproject.data.net.GankItemBean;
import com.info.lin.infoproject.data.net.MultiData;
import com.info.lin.infoproject.data.net.ZhiBeforeDailyResponse;
import com.info.lin.infoproject.data.net.ZhiDetailResponse;
import com.info.lin.infoproject.utils.AppUtils;
import com.info.lin.infoproject.utils.constant.Constants;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lin on 2017/2/22.
 */
public class RequestManager {

    private static final String TAG = "RequestManager";

    private static final int MAX_AGE = 4 * 60 * 60; // 缓存时间4小时
    private static final int CACHE_SIZE = 10 * 1024 * 1024; //缓存10M
    private SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    private static RequestManager sRequestManager;
    private GankApi mGankApi;
    private final ZhiApi mZhiApi;

    public RequestManager() {

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = null;
                try {
                    response = chain.proceed(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (request.url().toString().startsWith(Constants.GANK_BASE_URL) && response != null) {
                    return response.newBuilder()
                            .header("Cache-Control", "max-age=" + MAX_AGE)
                            .build();
                }
                return response;
            }
        };

        File cacheDirectory = new File(AppUtils.getCacheDir(), "responses");
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(new Cache(cacheDirectory, CACHE_SIZE))
                .addNetworkInterceptor(interceptor)
                .build();

        Retrofit gankRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.GANK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        Retrofit zhiRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.ZHI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mGankApi = gankRetrofit.create(GankApi.class);
        mZhiApi = zhiRetrofit.create(ZhiApi.class);
    }

    public static RequestManager getInstance() {
        if (sRequestManager == null) {
            synchronized (RequestManager.class) {
                if (sRequestManager == null) {
                    sRequestManager = new RequestManager();
                }
            }
        }
        return sRequestManager;
    }

    public Observable<List<GankItemBean>> getGirlData(int number, int page) {
        return mGankApi.getBeauties(number, page)
                .subscribeOn(Schedulers.io())
                .map(new Func1<GankBeautyResponse, List<GankItemBean>>() {
                    @Override
                    public List<GankItemBean> call(GankBeautyResponse gankBeautyResponse) {
                        return gankBeautyResponse.getResults();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<List<MultiData>> getGankData(String category, int count, int page) {
        final List<MultiData> multiDatas = new ArrayList<>();
        if (!TextUtils.equals(category, Constants.TYPE_DAILY)) {
            return mGankApi.getGankData(category, count, page)
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<GankDataResponse, List<MultiData>>() {
                        @Override
                        public List<MultiData> call(GankDataResponse gankDataResponse) {

                            List<GankItemBean> results = gankDataResponse.getResults();
                            if (results != null) {
                                for (GankItemBean gankItemBean : results) {
                                    multiDatas.add(new MultiData(MultiData.ITEM_DATA, gankItemBean));
                                }
                            }
                            return multiDatas;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return mGankApi.getDayHistory()
                .filter(new Func1<DayHistoryResponse, Boolean>() {
                    @Override
                    public Boolean call(DayHistoryResponse dayHistoryResponse) {
                        return dayHistoryResponse != null && dayHistoryResponse.getResults() != null
                                && dayHistoryResponse.getResults().size() > 0;
                    }
                })
                .map(new Func1<DayHistoryResponse, Calendar>() {
                    @Override
                    public Calendar call(DayHistoryResponse dayHistoryResponse) {
                        Calendar calendar = Calendar.getInstance(Locale.CHINA);
                        try {
                            calendar.setTime(dataFormat.parse(dayHistoryResponse.getResults().get(0)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            calendar = null;
                        }
                        return calendar;
                    }
                })
                .filter(new Func1<Calendar, Boolean>() {
                    @Override
                    public Boolean call(Calendar calendar) {
                        return calendar != null;
                    }
                })
                .flatMap(new Func1<Calendar, Observable<GankDailyResponse>>() {
                    @Override
                    public Observable<GankDailyResponse> call(Calendar calendar) {
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        return mGankApi.getDailyData(year, month, day);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<GankDailyResponse, List<MultiData>>() {
                    @Override
                    public List<MultiData> call(GankDailyResponse gankDailyResponse) {
                        DailyDataBean result = gankDailyResponse.getResult();
                        if (result != null) {

                            List<GankItemBean> benefitDataList = result.getBenefitDataList();
                            if (benefitDataList != null && benefitDataList.size() > 0) {
                                multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_BENEFIT));
                                for (GankItemBean bean : benefitDataList) {
                                    multiDatas.add(new MultiData(MultiData.ITEM_IMG, bean));
                                }
                            }

                            List<GankItemBean> androidList = result.getAndroidDataList();
                            if (androidList != null && androidList.size() > 0) {
                                multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_ANDROID));
                                for (GankItemBean bean : androidList) {
                                    multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
                                }
                            }

                            List<GankItemBean> iOSList = result.getiOSDataList();
                            if (iOSList != null && iOSList.size() > 0) {
                                multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, "iOS"));
                                for (GankItemBean bean : iOSList) {
                                    multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
                                }
                            }

                            List<GankItemBean> appList = result.getAppDataList();
                            if (appList != null && appList.size() > 0) {
                                multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_APP));
                                for (GankItemBean bean : appList) {
                                    multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
                                }
                            }

                            List<GankItemBean> webList = result.getWebDataList();
                            if (webList != null && webList.size() > 0) {
                                multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_WEB));
                                for (GankItemBean bean : webList) {
                                    multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
                                }
                            }

                            List<GankItemBean> videoList = result.getRestVideoDataList();
                            if (videoList != null && videoList.size() > 0) {
                                multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_REST_VIDEO));
                                for (GankItemBean bean : videoList) {
                                    multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
                                }
                            }

                            List<GankItemBean> recommendDataList = result.getRecommendDataList();
                            if (recommendDataList != null && recommendDataList.size() > 0) {
                                multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_RECOMMEND));
                                for (GankItemBean bean : recommendDataList) {
                                    multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
                                }
                            }

                            List<GankItemBean> expandResDataList = result.getExpandResDataList();
                            if (expandResDataList != null && expandResDataList.size() > 0) {
                                multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_EXPAND_RES));
                                for (GankItemBean bean : expandResDataList) {
                                    multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
                                }
                            }
                        }
                        return multiDatas;
                    }
                });
    }


    public Observable<List<DailyStory>> getZhiBeforeDailyData(String date) {
        return mZhiApi.getBeforeDaily(date)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ZhiBeforeDailyResponse, List<DailyStory>>() {
                    @Override
                    public List<DailyStory> call(ZhiBeforeDailyResponse zhiBeforeDailyResponse) {
                        return zhiBeforeDailyResponse.getStories();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Subscription getZhiDetailedStory(int storyId, final NormalCallBack<ZhiDetailResponse> callBack) {
        return mZhiApi.getStoryContent(storyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ZhiDetailResponse>() {
                    @Override
                    public void call(ZhiDetailResponse zhiDetailResponse) {
                        callBack.handle(zhiDetailResponse);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callBack.handleError();
                    }
                });
    }
}
