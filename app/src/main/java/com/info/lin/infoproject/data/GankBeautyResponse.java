package com.info.lin.infoproject.data;

import java.util.List;

/**
 * Created by lin on 2017/2/22.
 */
public class GankBeautyResponse extends GankBaseBean<GankItemBean>{

    private List<GankItemBean> results;

    public List<GankItemBean> getResults() {
        return results;
    }

    public void setResults(List<GankItemBean> results) {
        this.results = results;
    }

}
