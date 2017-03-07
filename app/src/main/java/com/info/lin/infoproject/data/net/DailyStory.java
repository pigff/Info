package com.info.lin.infoproject.data.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lin on 2017/3/7.
 */

public class DailyStory implements Serializable{

    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private List<String> images;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        if (images.size() == 0) {
            List<String> newImages = new ArrayList<>();
            newImages.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2057244741,2710616872&fm=23&gp=0.jpg");
            return newImages;
        }
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyStory that = (DailyStory) o;

        if (type != that.type) return false;
        return id == that.id;

    }

    @Override
    public int hashCode() {
        int result = type;
        result = 31 * result + id;
        return result;
    }
}
