package com.info.lin.infoproject.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.info.lin.infoproject.R;
import com.info.lin.infoproject.adapter.GirlAdapter;
import com.info.lin.infoproject.base.BaseRecyclerFragment;
import com.info.lin.infoproject.base.presenter.imp.GirlPresenter;
import com.info.lin.infoproject.data.net.GankItemBean;


public class GirlFragment extends BaseRecyclerFragment<GankItemBean, GirlAdapter, GirlPresenter> {


    public GirlFragment() {
    }


    public static GirlFragment newInstance() {
        GirlFragment fragment = new GirlFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void loadData(int pageNum) {
        getPresenter().getData(pageNum, mPageSize);
    }

    @Override
    protected GirlAdapter initRecyclerAdapter() {
        return new GirlAdapter(R.layout.recycler_card_girl_item);
    }

    @Override
    protected GirlPresenter initPresenter() {
        return new GirlPresenter();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        return layoutManager;
    }

    @Override
    public void initView() {
        super.initView();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                ((StaggeredGridLayoutManager) getLayoutManager()).invalidateSpanAssignments();
            }
        });

    }

    @Override
    protected boolean canRefresh() {
        return true;
    }

    @Override
    protected boolean canLoadMore() {
        return true;
    }
}
