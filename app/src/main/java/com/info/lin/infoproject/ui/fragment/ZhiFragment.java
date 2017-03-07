package com.info.lin.infoproject.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.info.lin.infoproject.R;
import com.info.lin.infoproject.data.net.DailyStory;
import com.info.lin.infoproject.data.net.ZhiDailyResponse;
import com.info.lin.infoproject.network.NormalCallBack;
import com.info.lin.infoproject.network.RequestManager;
import com.info.lin.infoproject.utils.ImgLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ZhiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZhiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private List<DailyStory> mResponses;
    private ZhiAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;


    public ZhiFragment() {
        // Required empty public constructor
    }

    public static ZhiFragment newInstance(String param1, String param2) {
        ZhiFragment fragment = new ZhiFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zhi, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_zhi);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_zhi);
        init();
        getFirstData();
        return view;
    }

    private void getFirstData() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                getData();
            }
        });
    }

    private void getData() {
        RequestManager.getInstance().getZhiDailyData(new NormalCallBack<ZhiDailyResponse>() {
            @Override
            public void success(ZhiDailyResponse zhiDailyResponse) {
                Log.d("ZhiFragment", "正确的");
                mAdapter.addData(zhiDailyResponse.getStories());
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void error() {
                Log.d("ZhiFragment", "错误啦");
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void init() {
        initData();

        initAdapter();

        initView();

        initListener();
    }

    private void initListener() {
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    private void initView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

//        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(R.color.md_blue_900_color_code, R.color.md_light_blue_700_color_code, R.color.md_light_blue_300_color_code);
    }

    private void initAdapter() {
        mAdapter = new ZhiAdapter(R.layout.recycler_zhi_card_item, mResponses);
    }

    private void initData() {
        mResponses = new ArrayList<>();
    }

    @Override
    public void onRefresh() {

    }

    class ZhiAdapter extends BaseQuickAdapter<DailyStory, BaseViewHolder> {

        public ZhiAdapter(int layoutResId, List<DailyStory> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, DailyStory item) {
            helper.setText(R.id.tv_title_zhi_card, item.getTitle());
            ImageView imageView = helper.getView(R.id.iv_zhi_card);
            ImgLoadUtils.loadUrl(mContext, item.getImages().get(0), imageView);
        }

        public void updateList(List<DailyStory> dailyStories) {
            if (!(dailyStories != null && !getData().containsAll(dailyStories))) {
                Toast.makeText(mContext, mContext.getString(R.string.recent_news_notice), Toast.LENGTH_SHORT).show();
            }
            setNewData(dailyStories);
        }
    }
}
