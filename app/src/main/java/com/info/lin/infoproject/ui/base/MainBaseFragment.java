package com.info.lin.infoproject.ui.base;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.info.lin.infoproject.adapter.ListMultiAdapter;
import com.info.lin.infoproject.base.BaseRecyclerFragment;
import com.info.lin.infoproject.base.presenter.imp.GankPresenter;
import com.info.lin.infoproject.data.net.MultiData;
import com.info.lin.infoproject.ui.WebContentActivity;


public abstract class MainBaseFragment extends BaseRecyclerFragment<MultiData, ListMultiAdapter, GankPresenter>  {

    public MainBaseFragment() {
    }

    @Override
    public void initListener() {
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MultiData data = ((MultiData) adapter.getItem(position));
                switch (adapter.getItemViewType(position)) {
                    case MultiData.ITEM_DATA:
                        Intent intent2Web = WebContentActivity.newInstance(getActivity(), data.getGankItemBean());
                        startActivity(intent2Web);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected ListMultiAdapter initRecyclerAdapter() {
        return new ListMultiAdapter(null);
    }

    @Override
    protected GankPresenter initPresenter() {
        return new GankPresenter();
    }

    @Override
    protected void loadData(int pageNum) {
        getPresenter().getData(getFragmentType(), mPageSize, ++pageNum);
    }

    public abstract String getFragmentType();

}
