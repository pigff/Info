package com.info.lin.infoproject.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.info.lin.infoproject.R;
import com.info.lin.infoproject.callback.TanTanCallback;
import com.info.lin.infoproject.data.net.GankBeautyResponse;
import com.info.lin.infoproject.data.net.GankItemBean;
import com.info.lin.infoproject.network.CallBack;
import com.info.lin.infoproject.network.RequestManager;
import com.info.lin.infoproject.ui.base.BaseFragment;
import com.info.lin.infoproject.utils.ImgLoadUtils;
import com.mcxtzhang.layoutmanager.swipecard.CardConfig;
import com.mcxtzhang.layoutmanager.swipecard.OverLayCardLayoutManager;
import com.mcxtzhang.layoutmanager.swipecard.RenRenCallback;

import java.util.ArrayList;
import java.util.List;


public class LikeFragment extends BaseFragment {


    private RecyclerView mRecyclerView;
    private List<GankItemBean> mItemBeen;
    private int mPage;
    private int mNumber;
    private LikeAdapter mAdapter;


    public LikeFragment() {
        // Required empty public constructor
    }

    public static LikeFragment newInstance() {
        LikeFragment fragment = new LikeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_like);
        init();
        getData();
        return view;
    }

    private void getData() {
        mSubscription = RequestManager.getInstance()
                .getGirlData(mNumber, mPage, new CallBack<GankBeautyResponse>() {
                    @Override
                    public void success(GankBeautyResponse gankBeautyResponse) {
                        mAdapter.addData(gankBeautyResponse.getResults());
                        mPage++;
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

    private void initView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new OverLayCardLayoutManager());
        mRecyclerView.setAdapter(mAdapter);

        CardConfig.initConfig(getActivity());
        Log.d("LikeFragment", "mItemBeen.size():" + mItemBeen.size());
        RenRenCallback callback = new TanTanCallback(mRecyclerView, mAdapter, mItemBeen);
        callback.setSwipCallBack(new RenRenCallback.SwipCallBack() {
            @Override
            public void swip(boolean isLeftSwip) {

            }
        });
        callback.setLoadCallBack(new RenRenCallback.LoadCallBack() {
            @Override
            public void load() {
                getData();
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initAdapter() {
        mAdapter = new LikeAdapter(R.layout.recycler_swipe_card_item, mItemBeen);
    }

    private void initData() {
        mPage = 1;
        mNumber = 15;
        mItemBeen = new ArrayList<>();
    }

    class LikeAdapter extends BaseQuickAdapter<GankItemBean, BaseViewHolder> {

        public LikeAdapter(int layoutResId, List<GankItemBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GankItemBean item) {
            ImgLoadUtils.loadUrl(mContext, item.getUrl(), (ImageView) helper.getView(R.id.iv_swip_card));
        }
    }
}
