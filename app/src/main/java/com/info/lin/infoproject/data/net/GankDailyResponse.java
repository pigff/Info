package com.info.lin.infoproject.data.net;

import java.util.List;

/**
 * Created by lin on 2017/2/23.
 */

public class GankDailyResponse extends GankBaseBean<DailyDataBean> {
    private DailyDataBean results;
    private List<String> categoryList;

    public DailyDataBean getResult() {
        return results;
    }

    public void setResult(DailyDataBean result) {
        this.results = result;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        return "GankDailyResponse{" +
                "categoryList=" + categoryList +
                ", result=" + results +
                '}';
    }
}
