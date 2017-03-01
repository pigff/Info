package com.info.lin.infoproject.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.info.lin.infoproject.R;
import com.info.lin.infoproject.adapter.ListMultiAdapter;
import com.info.lin.infoproject.data.net.DailyDataBean;
import com.info.lin.infoproject.data.net.GankDailyResponse;
import com.info.lin.infoproject.data.net.GankItemBean;
import com.info.lin.infoproject.data.net.MultiData;
import com.info.lin.infoproject.network.CallBack;
import com.info.lin.infoproject.network.RequestManager;
import com.info.lin.infoproject.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class MainBaseFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private ListMultiAdapter mAdapter;
    private boolean mIsLoad;
    private int mCount;
    private int mPage;

    public MainBaseFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_base, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_main_fragment);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_main_fragment);

        init();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RequestManager.getInstance().getDailyData(new CallBack<GankDailyResponse>() {
            @Override
            public void success(GankDailyResponse gankDailyResponse) {
                DailyDataBean result = gankDailyResponse.getResult();
                List<MultiData> multiDatas = new ArrayList<MultiData>();
                if (result != null) {

                    List<GankItemBean> benefitDataList = result.getBenefitDataList();
                    if (benefitDataList != null && benefitDataList.size() > 0) {
                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_BENEFIT));
                        for (GankItemBean bean : benefitDataList) {
                            multiDatas.add(new MultiData(MultiData.ITEM_IMG, bean));
                        }
                    }

                    List<GankItemBean> androidList = result.getAndroidDataList();
                    if (androidList != null && androidList.size() > 0) {
                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_ANDROID));
                        for (GankItemBean bean : androidList) {
                            multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
                        }
                    }

                    List<GankItemBean> iOSList = result.getiOSDataList();
                    if (iOSList != null && iOSList.size() > 0) {
                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, "iOS"));
                        for (GankItemBean bean : iOSList) {
                            multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
                        }
                    }

                    List<GankItemBean> appList = result.getAppDataList();
                    if (appList != null && appList.size() > 0) {
                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_APP));
                        for (GankItemBean bean : appList) {
                            multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
                        }
                    }

                    List<GankItemBean> webList = result.getWebDataList();
                    if (webList != null && webList.size() > 0) {
                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_ANDROID));
                        for (GankItemBean bean : webList) {
                            multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
                        }
                    }

                    List<GankItemBean> videoList = result.getRestVideoDataList();
                    if (videoList != null && videoList.size() > 0) {
                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_REST_VIDEO));
                        for (GankItemBean bean : videoList) {
                            multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
                        }
                    }

                    List<GankItemBean> recommendDataList = result.getRecommendDataList();
                    if (recommendDataList != null && recommendDataList.size() > 0) {
                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_RECOMMEND));
                        for (GankItemBean bean : recommendDataList) {
                            multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
                        }
                    }

                    List<GankItemBean> expandResDataList = result.getExpandResDataList();
                    if (expandResDataList != null && expandResDataList.size() > 0) {
                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_EXPAND_RES));
                        for (GankItemBean bean : expandResDataList) {
                            multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
                        }
                    }
                }
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
        mIsLoad = false;
        mCount = 15;
        mPage = 1;
    }

    private void initView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setEnabled(false);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(R.color.md_blue_900_color_code, R.color.md_light_blue_700_color_code, R.color.md_light_blue_300_color_code);
    }

    private void initAdapter() {
        List<MultiData> datas = new ArrayList<>();
        mAdapter = new ListMultiAdapter(datas);
        mAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}