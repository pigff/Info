package com.info.lin.infoproject.data.net;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by lin on 2017/2/23.
 */

public class MultiData implements MultiItemEntity {

    public static final int ITEM_DATA = 0;
    public static final int ITEM_IMG = 1;
    public static final int ITEM_SORT_LINE = 2;

    private int itemType;
    private String name;
    private GankItemBean gankItemBean;

    public MultiData(int itemType, String name) {
        this.itemType = itemType;
        this.name = name;
    }

    public MultiData(int itemType, GankItemBean gankItemBean) {
        this.itemType = itemType;
        this.gankItemBean = gankItemBean;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GankItemBean getGankItemBean() {
        return gankItemBean;
    }

    public void setGankItemBean(GankItemBean gankItemBean) {
        this.gankItemBean = gankItemBean;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
