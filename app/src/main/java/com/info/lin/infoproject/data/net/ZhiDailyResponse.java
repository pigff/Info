package com.info.lin.infoproject.data.net;

import java.util.List;

/**
 * Created by lin on 2017/3/7.
 */

public class ZhiDailyResponse {

    private String date;


    private List<DailyStory> stories;


    private List<DailyStory> top_stories;

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

    public List<DailyStory> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<DailyStory> top_stories) {
        this.top_stories = top_stories;
    }

}
