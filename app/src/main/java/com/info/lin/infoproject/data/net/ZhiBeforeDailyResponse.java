package com.info.lin.infoproject.data.net;

import java.util.List;

/**
 * Created by lin on 2017/3/7.
 */

public class ZhiBeforeDailyResponse {
    private String date;

    private List<DailyStory> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DailyStory> getStories() {
        return stories;
    }

    public void setStories(List<DailyStory> stories) {
        this.stories = stories;
    }
}
