package com.info.lin.infoproject.ui.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.info.lin.infoproject.R;
import com.info.lin.infoproject.data.net.GankBeautyResponse;
import com.info.lin.infoproject.data.net.GankItemBean;
import com.info.lin.infoproject.network.CallBack;
import com.info.lin.infoproject.network.RequestManager;
import com.info.lin.infoproject.ui.MeizhiPicActivity;
import com.info.lin.infoproject.ui.base.BaseFragment;
import com.info.lin.infoproject.utils.AppUtils;
import com.info.lin.infoproject.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GirlFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private GirlAdapter mAdapter;
    private int mPage;
    private int mNumber;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean mIsLoad;


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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_girl);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_girl);
        init();
        FirstGetData();
        initListener();
        return view;
    }

    private void initListener() {
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent2Pic = new Intent(getActivity(), MeizhiPicActivity.class);
                intent2Pic.putExtra(Constants.DATA_INTENT, ((GankItemBean) adapter.getItem(position)).getUrl());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), view, MeizhiPicActivity.TRANSIT_PIC);
                try {
                    ActivityCompat.startActivity(getActivity(), intent2Pic, optionsCompat.toBundle());
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    startActivity(intent2Pic);
                }
            }
        });
    }

    private void FirstGetData() {
        mIsLoad = true;
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                getData();
            }
        });
    }

    private void getData() {
        mSubscription = RequestManager.getInstance()
                .getGirlData(mNumber, mPage, new CallBack<GankBeautyResponse>() {
                    @Override
                    public void success(GankBeautyResponse gankBeautyResponse) {

                        if (mPage == 1) {
                            mAdapter.setNewData(gankBeautyResponse.getResults());
                        } else {
                            mAdapter.addData(gankBeautyResponse.getResults());
                        }
                        handleNet(true);
                        mPage++;
                    }

                    @Override
                    public void error() {
                        handleNet(false);
                    }
                });
    }

    private void handleNet(boolean isSuccess) {
        mIsLoad = false;
        if (!isSuccess) {
            if (mRefreshLayout.isRefreshing()) {
                mRefreshLayout.setRefreshing(false);
            }
            if (mAdapter.isLoading()) {
                mAdapter.loadMoreFail();
            }
        } else if (mPage == 1) {
            mRefreshLayout.setRefreshing(false);
        } else if (mPage >= Constants.ZHI_MAX_PAGE || mAdapter.getData().size() % 15 != 0) {
            Log.d("GirlFragment", "mAdapter.getData().size():" + mAdapter.getData().size());
            mAdapter.loadMoreEnd();
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    private void init() {
        initData();

        initAdapter();

        initView();
    }

    private void initData() {
        mPage = 1;
        mNumber = 15;
        mIsLoad = false;
    }

    private void initView() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(R.color.md_blue_900_color_code, R.color.md_light_blue_700_color_code, R.color.md_light_blue_300_color_code);

        mRecyclerView.setHasFixedSize(true);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments();
            }
        });

    }

    private void initAdapter() {
        List<GankItemBean> gankItemBeen = new ArrayList<>();
        mAdapter = new GirlAdapter(R.layout.recycler_card_girl_item, gankItemBeen);
//        mAdapter.openLoadAnimation();
        mAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        if (!mIsLoad) {
            getData();
            mIsLoad = true;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (!mIsLoad) {
            getData();
            mIsLoad = true;
        }
    }

    private class GirlAdapter extends BaseQuickAdapter<GankItemBean, BaseViewHolder> {

        private int[] colrSrc = {R.color.md_light_blue_700_color_code, R.color.md_yellow_300_color_code, R.color.md_light_blue_300_color_code};
        private Map<String, Integer> mMap;

        GirlAdapter(int layoutResId, List<GankItemBean> data) {
            super(layoutResId, data);
            mMap = new HashMap<>();
        }

        @Override
        protected void convert(final BaseViewHolder helper, final GankItemBean item) {
            helper.addOnClickListener(R.id.iv_girl_card);
            final ImageView imageView =  helper.getView(R.id.iv_girl_card);
            if (!mMap.containsKey(item.get_id())) {
                Glide.with(mContext).load(item.getUrl()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new SimpleTarget<Bitmap>(AppUtils.getScreenWidth() / 2, AppUtils.getScreenHeight() / 2) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                int width = resource.getWidth();
                                int height = resource.getHeight();
                                int realHeight = (AppUtils.getScreenWidth() / 2) * height / width;
                                mMap.put(item.get_id(), realHeight);
                                ViewGroup.LayoutParams lp = imageView.getLayoutParams();
                                lp.height = realHeight;
                                imageView.setLayoutParams(lp);
//
                            }
                        });
            } else {
                ViewGroup.LayoutParams lp = imageView.getLayoutParams();
                lp.height = mMap.get(item.get_id());
                imageView.setLayoutParams(lp);

            }
            Glide.with(mContext)
                    .load(item.getUrl())
                    .dontAnimate()
                    .placeholder(colrSrc[helper.getAdapterPosition() % 3])
                    .into(imageView);
        }
    }

}
