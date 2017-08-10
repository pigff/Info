package com.info.lin.infoproject.base.presenter.imp;

import com.info.lin.infoproject.base.presenter.ICommonPresenter;
import com.info.lin.infoproject.data.net.GankItemBean;
import com.info.lin.infoproject.network.RequestManager;
import com.info.lin.infoproject.subscribers.SimpleSubscriber;

import java.util.List;

/**
 * Created by greedy on 2017/8/6.
 */

public class GirlPresenter extends ListPresenter<GankItemBean> implements ICommonPresenter{
    @Override
    public void getData(int pageNum, int pageSize) {
        RequestManager
                .getInstance()
                .getGirlData(pageNum, pageSize)
                .compose(getView().<List<GankItemBean>>bindToLife())
                .subscribe(new SimpleSubscriber<>(getLoadSimpleListener()));

    }
}
