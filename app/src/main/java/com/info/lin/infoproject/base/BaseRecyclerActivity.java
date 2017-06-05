package com.info.lin.infoproject.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.info.lin.infoproject.R;
import com.info.lin.infoproject.base.presenter.imp.ListPresenter;
import com.info.lin.infoproject.base.view.IListView;
import com.info.lin.infoproject.utils.LogUtil;

import java.util.List;

/**
 * Created by greedy on 17/3/14.
 */

public abstract class BaseRecyclerActivity<T, V extends BaseQuickAdapter<T, ? extends BaseViewHolder>, P extends ListPresenter<T>>
        extends BaseToolbarActivity<P> implements IListView<T> {

    public static final int DEFAULT_PAGENUM = 0;
    public static final int DEFAULT_PAGESIZE = 20;

    public static final int NORMAL_STATUS = 1000;
    public static final int LOAD_STATUS = 1001;
    public static final int REFRESH_STATUS = 1002;

    protected V mAdapter;
    protected RecyclerView mRecyclerView;
    protected int mPageNum;
    protected int mPageSize;
    private int mStatus;

    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Override
    protected int provideContentView() {
        return R.layout.activity_common_rv_list;
    }

    @Override
    public void initData() {
        mPageNum = DEFAULT_PAGENUM;
        mPageSize = DEFAULT_PAGESIZE;
        mStatus = NORMAL_STATUS;
    }

    @Override
    public void initAdapter() {
        mAdapter = initRecyclerAdapter();
        if (canLoadMore()) {
            mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if (mStatus == NORMAL_STATUS) {
                        mStatus = LOAD_STATUS;
                        loadData(mPageNum);
                    }
                }
            }, mRecyclerView);
        }
        if (canLoadMore() && openLoadAnim()) {
            mAdapter.openLoadAnimation();
        }
    }

    @Override
    public void initView() {
        setTitle(getToolbarTitle());
        super.initView();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_common_activity);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_common_activity);
        if (mRecyclerView == null || mRefreshLayout == null) {
            throw new IllegalStateException(
                    "The subclass of ToolbarActivity must contain recyclerview and refreshLayout");
        }

        initRecycler();
        if (canRefresh()) {
            initRefreshLayout();
        } else {
            mRefreshLayout.setEnabled(false);
        }
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mStatus == NORMAL_STATUS) {
                    mStatus = REFRESH_STATUS;
                    loadData(DEFAULT_PAGENUM);
                }
            }
        });
    }

    private void initRecycler() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(getLayoutManager());
//        mRecyclerView.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(mRecyclerView);
    }

    @Override
    public void loadData(List<T> data) {
        LogUtil.d(TAG, "status: " + mStatus);
        if (mPageNum == 0 && data.size() == 0) {
            showEmpty();
            if (mStatus == REFRESH_STATUS) {
                finishRefresh();
            }
            mStatus = NORMAL_STATUS;
            return;
        }
        if (mPageNum == DEFAULT_PAGENUM) {
            hide();
        }
        if (mStatus == REFRESH_STATUS) {
            finishRefresh();
            mPageNum = DEFAULT_PAGENUM + 1;
            getAdapter().setNewData(data);
        } else if (mStatus == LOAD_STATUS) {
            getAdapter().addData(data);
            if (canLoadMore()) {
                if (data.size() < mPageSize) {
                    if (mPageNum == DEFAULT_PAGENUM) {
                        // 不显示没有更多
                        getAdapter().loadMoreEnd(true);
                    } else {
                        //显示没有更多
                        getAdapter().loadMoreEnd();
                    }
                } else {
                    getAdapter().loadMoreComplete();
                    // 页码增加 需要在View层里面做处理
//                            pageNum++;
                }
            }
            mPageNum++;
        }
        mStatus = NORMAL_STATUS;
    }

    @Override
    public void loadError() {
        LogUtil.d(TAG, "status: " + mStatus);
        if (mStatus == REFRESH_STATUS) {
            finishRefresh();
            if (mPageNum == DEFAULT_PAGENUM) {
                showError();
            }
        }
        if (mStatus == LOAD_STATUS) {
            if (mPageNum == DEFAULT_PAGENUM) {
                showError();
            } else {
                getAdapter().loadMoreFail();
            }
        }
        mStatus = NORMAL_STATUS;
    }

    @Override
    public void initListener() {

    }


    @Override
    protected boolean hasBaseLayout() {
        return true;
    }

    protected abstract String getToolbarTitle();

    protected abstract V initRecyclerAdapter();

    private void getData() {
        mStatus = LOAD_STATUS;
        loadData(mPageNum);
    }

    protected boolean canLoadMore() {
        return false;
    }

    protected boolean canRefresh() {
        return false;
    }

    protected boolean openLoadAnim() {
        return false;
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    private V getAdapter() {
        if (mAdapter == null) {
            mAdapter = initRecyclerAdapter();
        }
        return mAdapter;
    }

    protected void finishRefresh() {
        if (canRefresh() && mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }


//    protected void loadMoreData() {
//
//    }
//
//    protected void refreshData() {
//
//    }

    protected void loadData(int pageNum) {

    }
}
