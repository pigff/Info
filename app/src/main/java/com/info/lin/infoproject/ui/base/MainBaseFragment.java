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
        getPresenter().getData(getFragmentType(), mPageSize, pageNum);
    }

    public abstract String getFragmentType();

    //    private void getOtherData() {
//        mSubscription = RequestManager.getInstance().getGankData(getFragmentType(), mCount, ++mPage, new CallBack<GankDataResponse>() {
//            @Override
//            public void success(GankDataResponse gankDataResponse) {
//                List<MultiData> multiDatas = new ArrayList<>();
//                List<GankItemBean> results = gankDataResponse.getResults();
//                if (results != null) {
//                    for (GankItemBean gankItemBean : results) {
//                        multiDatas.add(new MultiData(MultiData.ITEM_DATA, gankItemBean));
//                    }
//                }
//                if (mIsRefresh) {
//                    mAdapter.updateList(multiDatas);
//                    mRefreshLayout.setRefreshing(false);
//                    mIsRefresh = false;
//                }
//                if (mIsLoadMore) {
//                    mPage++;
//                    mAdapter.addData(multiDatas);
//                    if (multiDatas.size() < mCount) {
//                        mAdapter.loadMoreEnd();
//                    } else {
//                        mAdapter.loadMoreComplete();
//                    }
//                    mIsLoadMore = false;
//                }
//                Log.d(TAG, "mAdapter.getData().size():" + mAdapter.getData().size());
//            }
//
//            @Override
//            public void error() {
//
//            }
//        });
//    }

//    private void getDailyData() {
//        mSubscription = RequestManager.getInstance().getDailyData(new CallBack<GankDailyResponse>() {
//            @Override
//            public void success(GankDailyResponse gankDailyResponse) {
//                DailyDataBean result = gankDailyResponse.getResult();
//                List<MultiData> multiDatas = new ArrayList<MultiData>();
//                if (result != null) {
//
//                    List<GankItemBean> benefitDataList = result.getBenefitDataList();
//                    if (benefitDataList != null && benefitDataList.size() > 0) {
//                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_BENEFIT));
//                        for (GankItemBean bean : benefitDataList) {
//                            multiDatas.add(new MultiData(MultiData.ITEM_IMG, bean));
//                        }
//                    }
//
//                    List<GankItemBean> androidList = result.getAndroidDataList();
//                    if (androidList != null && androidList.size() > 0) {
//                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_ANDROID));
//                        for (GankItemBean bean : androidList) {
//                            multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
//                        }
//                    }
//
//                    List<GankItemBean> iOSList = result.getiOSDataList();
//                    if (iOSList != null && iOSList.size() > 0) {
//                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, "iOS"));
//                        for (GankItemBean bean : iOSList) {
//                            multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
//                        }
//                    }
//
//                    List<GankItemBean> appList = result.getAppDataList();
//                    if (appList != null && appList.size() > 0) {
//                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_APP));
//                        for (GankItemBean bean : appList) {
//                            multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
//                        }
//                    }
//
//                    List<GankItemBean> webList = result.getWebDataList();
//                    if (webList != null && webList.size() > 0) {
//                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_ANDROID));
//                        for (GankItemBean bean : webList) {
//                            multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
//                        }
//                    }
//
//                    List<GankItemBean> videoList = result.getRestVideoDataList();
//                    if (videoList != null && videoList.size() > 0) {
//                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_REST_VIDEO));
//                        for (GankItemBean bean : videoList) {
//                            multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
//                        }
//                    }
//
//                    List<GankItemBean> recommendDataList = result.getRecommendDataList();
//                    if (recommendDataList != null && recommendDataList.size() > 0) {
//                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_RECOMMEND));
//                        for (GankItemBean bean : recommendDataList) {
//                            multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
//                        }
//                    }
//
//                    List<GankItemBean> expandResDataList = result.getExpandResDataList();
//                    if (expandResDataList != null && expandResDataList.size() > 0) {
//                        multiDatas.add(new MultiData(MultiData.ITEM_SORT_LINE, Constants.TYPE_EXPAND_RES));
//                        for (GankItemBean bean : expandResDataList) {
//                            multiDatas.add(new MultiData(MultiData.ITEM_DATA, bean));
//                        }
//                    }
//                }
//                if (mIsRefresh) {
//                    mAdapter.updateList(multiDatas);
//                    mRefreshLayout.setRefreshing(false);
//                    mIsRefresh = false;
//                }
//                Log.d(TAG, "mAdapter.getData().size():" + mAdapter.getData().size());
//            }
//
//            @Override
//            public void error() {
//
//            }
//        });
//    }


//    private void getData() {
//        if (TextUtils.equals(getFragmentType(), Constants.TYPE_DAILY)) {
//            getDailyData();
//        } else {
//            getOtherData();
//        }
//    }


}
