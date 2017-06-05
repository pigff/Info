package com.info.lin.infoproject.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.info.lin.infoproject.R;
import com.info.lin.infoproject.base.presenter.imp.BasePresenter;
import com.info.lin.infoproject.base.view.IBaseView;
import com.info.lin.infoproject.utils.LogUtil;
import com.info.lin.infoproject.widget.EmptyLayout;
import com.trello.rxlifecycle.LifecycleTransformer;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lin on 2017/5/11.
 */

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity implements IBaseView, EmptyLayout.OnBaseLayoutClickListener {

    private P mPresenter;

    protected EmptyLayout mEmptyLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, getClass().getSimpleName());
        setContentView(provideContentView());
        if (hasBaseLayout()) {
            mEmptyLayout = (EmptyLayout) findViewById(R.id.emptylayout);
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
            mEmptyLayout.setOnBaseLayoutClickListener(this);
        }
        if (registEventBus()) {
            EventBus.getDefault().register(this);
        }
        init();
        if (getPresenter() != null) {
            getPresenter().attachView(this);
        }
    }

    private void init() {
        initData();
        initAdapter();
        initView();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (registEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void showEmpty() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_DATA);
        }
    }

    @Override
    public void showError() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
        }
    }

    @Override
    public void showLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
        }
    }

    @Override
    public void hide() {
        if (mEmptyLayout != null && mEmptyLayout.getEmptyStatus() != EmptyLayout.STATUS_HIDE) {
            mEmptyLayout.hide();
        }
    }


    /**
     * 初始化适配器
     */
    protected void initAdapter() {

    }

    /**
     * 初始化事件点击监听
     */
    protected void initListener() {

    }

    protected abstract void initData();

    protected abstract void initView();


    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindToLifecycle();
    }

    @Override
    public void onClickRetry() {

    }

    @Override
    public void onClickEmpty() {

    }

    protected abstract P initPresenter();

    protected P getPresenter() {
        if (mPresenter == null) {
            mPresenter = initPresenter();
        }
        return mPresenter;
    }

    protected boolean registEventBus() {
        return false;
    }

    protected abstract int provideContentView();

    protected boolean hasBaseLayout() {
        return false;
    }
}
