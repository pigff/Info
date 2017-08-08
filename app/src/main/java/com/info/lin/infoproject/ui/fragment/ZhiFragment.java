package com.info.lin.infoproject.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.info.lin.infoproject.R;
import com.info.lin.infoproject.adapter.ZhiAdapter;
import com.info.lin.infoproject.base.BaseRecyclerFragment;
import com.info.lin.infoproject.base.presenter.imp.ZhiPresenter;
import com.info.lin.infoproject.data.net.DailyStory;
import com.info.lin.infoproject.ui.ZhiWebActivity;
import com.info.lin.infoproject.utils.AppUtils;
import com.info.lin.infoproject.utils.constant.Constants;

import java.util.List;

public class ZhiFragment extends BaseRecyclerFragment<DailyStory, ZhiAdapter, ZhiPresenter> {

    public ZhiFragment() {
        // Required empty public constructor
    }

    public static ZhiFragment newInstance() {
        ZhiFragment fragment = new ZhiFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void loadData(List<DailyStory> data) {
        if (mPageNum == 0 && data.size() == 0) {
            showEmpty();
        }
        hide();
        if (getStatus() == REFRESH_STATUS) {
            getAdapter().updateList(data);
            finishRefresh();
            mPageNum = 1;
        }
        if (getStatus() == LOAD_STATUS) {
            getAdapter().addData(data);
            mPageNum++;
            if (mPageNum >= Constants.ZHI_MAX_PAGE) {
                getAdapter().loadMoreEnd();
            } else {
                getAdapter().loadMoreComplete();
            }
        }
        setStatus(NORMAL_STATUS);
    }

    @Override
    public void loadError() {
        if (getStatus() == REFRESH_STATUS) {
            finishRefresh();
            showError();
        }
        if (getStatus() == LOAD_STATUS) {
            getAdapter().loadMoreFail();
        }
        mPageNum--;
        setStatus(NORMAL_STATUS);
    }

    @Override
    public void initListener() {
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent2ZhiDetailed = new Intent(getActivity(), ZhiWebActivity.class);
                intent2ZhiDetailed.putExtra(Constants.DATA_INTENT, ((DailyStory) adapter.getItem(position)).getId());
                startActivity(intent2ZhiDetailed);
            }
        });
    }

    @Override
    protected ZhiAdapter initRecyclerAdapter() {
        return new ZhiAdapter(R.layout.recycler_zhi_card_item);
    }

    @Override
    protected ZhiPresenter initPresenter() {
        return new ZhiPresenter();
    }

    @Override
    protected void loadData(int pageNum) {
        String date = AppUtils.getZhiData(pageNum);
        getPresenter().getZhiData(date);
    }

    @Override
    protected boolean canLoadMore() {
        return true;
    }

    @Override
    protected boolean canRefresh() {
        return true;
    }
}
