package com.info.lin.infoproject.data.net;

import com.google.gson.annotations.SerializedName;
import com.info.lin.infoproject.utils.Constants;

import java.util.List;

/**
 * Created by lin on 2017/2/23.
 */

public class DailyDataBean {

    @SerializedName(Constants.TYPE_ANDROID)
    private List<GankItemBean> androidDataList;
    @SerializedName(Constants.TYPE_EXPAND_RES)
    private List<GankItemBean> expandResDataList;
    @SerializedName(Constants.TYPE_IOS)
    private List<GankItemBean> iOSDataList;
    @SerializedName(Constants.TYPE_WEB)
    private List<GankItemBean> webDataList;
    @SerializedName(Constants.TYPE_BENEFIT)
    private List<GankItemBean> benefitDataList;
    @SerializedName(Constants.TYPE_APP)
    private List<GankItemBean> appDataList;
    @SerializedName(Constants.TYPE_REST_VIDEO)
    private List<GankItemBean> restVideoDataList;
    @SerializedName(Constants.TYPE_RECOMMEND)
    private List<GankItemBean> recommendDataList;

    public List<GankItemBean> getAndroidDataList() {
        return androidDataList;
    }

    public void setAndroidDataList(List<GankItemBean> androidDataList) {
        this.androidDataList = androidDataList;
    }

    public List<GankItemBean> getExpandResDataList() {
        return expandResDataList;
    }

    public void setExpandResDataList(List<GankItemBean> expandResDataList) {
        this.expandResDataList = expandResDataList;
    }

    public List<GankItemBean> getiOSDataList() {
        return iOSDataList;
    }

    public void setiOSDataList(List<GankItemBean> iOSDataList) {
        this.iOSDataList = iOSDataList;
    }

    public List<GankItemBean> getWebDataList() {
        return webDataList;
    }

    public void setWebDataList(List<GankItemBean> webDataList) {
        this.webDataList = webDataList;
    }

    public List<GankItemBean> getBenefitDataList() {
        return benefitDataList;
    }

    public void setBenefitDataList(List<GankItemBean> benefitDataList) {
        this.benefitDataList = benefitDataList;
    }

    public List<GankItemBean> getAppDataList() {
        return appDataList;
    }

    public void setAppDataList(List<GankItemBean> appDataList) {
        this.appDataList = appDataList;
    }

    public List<GankItemBean> getRestVideoDataList() {
        return restVideoDataList;
    }

    public void setRestVideoDataList(List<GankItemBean> restVideoDataList) {
        this.restVideoDataList = restVideoDataList;
    }

    public List<GankItemBean> getRecommendDataList() {
        return recommendDataList;
    }

    public void setRecommendDataList(List<GankItemBean> recommendDataList) {
        this.recommendDataList = recommendDataList;
    }

    @Override
    public String toString() {
        return "DailyDataBean{" +
                "recommendDataList=" + recommendDataList +
                ", restVideoDataList=" + restVideoDataList +
                ", appDataList=" + appDataList +
                ", benefitDataList=" + benefitDataList +
                ", webDataList=" + webDataList +
                ", iOSDataList=" + iOSDataList +
                ", expandResDataList=" + expandResDataList +
                ", androidDataList=" + androidDataList +
                '}';
    }
}
