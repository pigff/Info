package com.info.lin.infoproject.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.info.lin.infoproject.R;
import com.info.lin.infoproject.adapter.GirlAdapter;
import com.info.lin.infoproject.base.BaseRecyclerFragment;
import com.info.lin.infoproject.base.presenter.imp.GirlPresenter;
import com.info.lin.infoproject.data.net.GankItemBean;
import com.info.lin.infoproject.ui.MeizhiPicActivity;
import com.info.lin.infoproject.utils.constant.Constants;


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
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    public void initView() {
        super.initView();
    }


    @Override
    public void initListener() {
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent2Pic = new Intent(getActivity(), MeizhiPicActivity.class);
                intent2Pic.putExtra(Constants.DATA_INTENT, ((GankItemBean) adapter.getItem(position)).getUrl());
                startActivity(intent2Pic);
                getActivity().overridePendingTransition(R.anim.common_whole_right_in, R.anim.common_part_left_out);
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
