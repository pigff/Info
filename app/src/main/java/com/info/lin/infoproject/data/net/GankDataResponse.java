package com.info.lin.infoproject.data.net;

import java.util.List;

/**
 * Created by lin on 2017/2/23.
 */

public class GankDataResponse extends GankBaseBean<GankItemBean> {

    private List<GankItemBean> results;

    public List<GankItemBean> getResults() {
        return results;
    }

    public void setResults(List<GankItemBean> results) {
        this.results = results;
    }
}
