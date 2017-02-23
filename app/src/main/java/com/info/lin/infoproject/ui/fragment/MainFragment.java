package com.info.lin.infoproject.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.info.lin.infoproject.R;
import com.info.lin.infoproject.ui.base.BaseFragment;
import com.info.lin.infoproject.ui.base.MainBaseFragment;
import com.info.lin.infoproject.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String[] mTitles = {Constants.TYPE_DAILY, Constants.TYPE_ANDROID, Constants.TYPE_IOS,
                                        Constants.TYPE_WEB, Constants.TYPE_APP, Constants.TYPE_EXPAND_RES};

    private String mParam1;
    private String mParam2;
    private List<MainBaseFragment> mFragments;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MainBaseFragmentAdapter mAdapter;


    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_main_fragment);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_main_fragment);

        init();
        return view;
    }

    private void init() {
        initData();

        initAdapter();

        initView();
    }

    private void initView() {
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initAdapter() {
        mAdapter = new MainBaseFragmentAdapter(getChildFragmentManager(), mFragments, mTitles);
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(DailyFragment.newInstance());
        mFragments.add(AndroidFragment.newInstance());
        mFragments.add(IosFragment.newInstance());
        mFragments.add(WebFragment.newInstance());
        mFragments.add(AppFragment.newInstance());
        mFragments.add(ExpandFragment.newInstance());
    }

    class MainBaseFragmentAdapter extends FragmentPagerAdapter {

        private List<MainBaseFragment> mFragments;
        private String[] mTitles;
        public MainBaseFragmentAdapter(FragmentManager fm, List<MainBaseFragment> fragments, String[] titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

}
