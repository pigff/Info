package com.info.lin.infoproject.base.view;

import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * Created by greedy on 2017/5/11.
 */

public interface IBaseView {

    void showError();

    void showLoading();

    void hide();

    void showEmpty();

    <T> LifecycleTransformer<T> bindToLife();
}
