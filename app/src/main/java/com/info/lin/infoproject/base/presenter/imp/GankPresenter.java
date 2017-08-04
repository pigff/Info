package com.info.lin.infoproject.base.presenter.imp;

import com.info.lin.infoproject.base.presenter.IGankPresenter;
import com.info.lin.infoproject.data.net.MultiData;
import com.info.lin.infoproject.network.RequestManager;
import com.info.lin.infoproject.subscribers.SimpleSubscriber;

import java.util.List;

/**
 * Created by greedy on 2017/8/3.
 */

public class GankPresenter extends ListPresenter<MultiData> implements IGankPresenter {

    @Override
    public void getData(String type, int pageSize, int pageNum) {
        RequestManager
                .getInstance()
                .getGankData(type, pageSize, pageNum)
                .compose(getView().<List<MultiData>>bindToLife())
                .subscribe(new SimpleSubscriber<>(getLoadSimpleListener()));
    }
}
