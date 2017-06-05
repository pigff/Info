package com.info.lin.infoproject.base.presenter;


import com.info.lin.infoproject.base.view.IBaseView;

/**
 * Created by greedy on 2017/5/11.
 */

public interface IPresenter<V extends IBaseView> {

    void attachView(V view);

    void detachView();
}
