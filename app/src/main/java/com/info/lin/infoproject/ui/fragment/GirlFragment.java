package com.info.lin.infoproject.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.info.lin.infoproject.R;
import com.info.lin.infoproject.data.GankBeautyResult;
import com.info.lin.infoproject.network.CallBack;
import com.info.lin.infoproject.network.RequestManager;
import com.info.lin.infoproject.ui.BaseFragment;
import com.info.lin.infoproject.utils.ImgLoadUtils;

import java.util.ArrayList;
import java.util.List;


public class GirlFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private GirlAdapter mAdapter;
    private int mPage;
    private int mNumber;


    public GirlFragment() {
    }


    public static GirlFragment newInstance(String param1, String param2) {
        GirlFragment fragment = new GirlFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.girl_rv);
        init();
        getData();
        return view;
    }

    private void getData() {
        mSubscription = RequestManager.getInstance()
                .getGirlData(20, 1, new CallBack<GankBeautyResult>() {
                    @Override
                    public void success(GankBeautyResult gankBeautyResult) {

                        mAdapter.setNewData(gankBeautyResult.getResults());
                    }

                    @Override
                    public void error() {
                    }
                });
    }

    private void init() {
        initData();

        initAdapter();

        initView();
    }

    private void initData() {
        mPage = 0;
        mNumber = 10;
    }

    private void initView() {
        mRecyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initAdapter() {
        List<GankBeautyResult.BeautyResult> beautyResults = new ArrayList<>();
        mAdapter = new GirlAdapter(R.layout.girl_card_item, beautyResults);
    }

    class GirlAdapter extends BaseQuickAdapter<GankBeautyResult.BeautyResult, BaseViewHolder> {


        public GirlAdapter(int layoutResId, List<GankBeautyResult.BeautyResult> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GankBeautyResult.BeautyResult item) {
            ImgLoadUtils.loadUrl(mContext, item.getUrl(), R.drawable.img_load_error
                    ,(ImageView) helper.getView(R.id.iv_girl_card), 600, 600);
        }
    }

}
