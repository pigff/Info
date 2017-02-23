package com.info.lin.infoproject.data;

import java.util.List;

/**
 * Created by lin on 2017/2/23.
 */

public class DayHistoryResponse extends GankBaseBean<String>{

    private List<String> results;

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }
}
