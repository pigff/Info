package com.info.lin.infoproject.network;

import com.info.lin.infoproject.data.GankBeautyResult;
import com.info.lin.infoproject.data.api.GankApi;
import com.info.lin.infoproject.utils.AppUtils;
import com.info.lin.infoproject.utils.Contants;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lin on 2017/2/22.
 */
public class RequestManager {

    private static final String TAG = "RequestManager";

    private static final int MAX_AGE = 4 * 60 * 60; // 缓存时间4小时
    private static final int CACHE_SIZE = 10 * 1024 * 1024; //缓存10M

    private static  RequestManager sRequestManager;
    private GankApi mGankApi;

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
                if(request.url().toString().startsWith(Contants.GANK_BASE_URL) && response != null) {
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

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Contants.GANK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mGankApi = retrofit.create(GankApi.class);
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

    public Subscription getGirlData(int number, int page, final CallBack<GankBeautyResult> callBack) {
        return mGankApi.getBeauties(number, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GankBeautyResult>() {
                    @Override
                    public void call(GankBeautyResult gankBeautyResult) {
                        callBack.handle(gankBeautyResult);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callBack.handleError();
                    }
                });
    }
}
