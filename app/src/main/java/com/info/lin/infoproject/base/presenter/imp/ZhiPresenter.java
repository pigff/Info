package com.info.lin.infoproject.base.presenter.imp;

import com.info.lin.infoproject.base.presenter.IZhiPresenter;
import com.info.lin.infoproject.data.net.DailyStory;
import com.info.lin.infoproject.network.RequestManager;
import com.info.lin.infoproject.subscribers.SimpleSubscriber;

import java.util.List;

/**
 * Created by greedy on 2017/8/5.
 */

public class ZhiPresenter extends ListPresenter<DailyStory> implements IZhiPresenter {
    @Override
    public void getZhiData(String date) {
        RequestManager
                .getInstance()
                .getZhiBeforeDailyData(date)
                .compose(getView().<List<DailyStory>>bindToLife())
                .subscribe(new SimpleSubscriber<>(getLoadSimpleListener()));

    }
}
