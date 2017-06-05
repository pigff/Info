package com.info.lin.infoproject.base.presenter.imp;

import com.info.lin.infoproject.base.presenter.IPresenter;
import com.info.lin.infoproject.base.view.IBaseView;

/**
 * Created by greedy on 2017/5/11.
 */

public class BasePresenter<V extends IBaseView> implements IPresenter<V> {

    private V mvpView;


    @Override
    public void attachView(V view) {
        mvpView = view;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    public boolean isAttachView() {
        return mvpView != null;
    }

    public void checkViewAttach() {
        if (!isAttachView()) {
            throw new MvpViewNotAttachedException();
        }
    }

    protected V getView() {
        checkViewAttach();
        return mvpView;
    }

    /**
     * 自定义异常
     */
    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("请求数据前请先调用 attachView(MvpView) 方法与View建立连接");
        }
    }
}
