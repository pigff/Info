package com.info.lin.infoproject.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.info.lin.infoproject.R;
import com.info.lin.infoproject.adapter.MainPagerAdapter;
import com.info.lin.infoproject.adapter.ScienceDrawerAdapter;
import com.info.lin.infoproject.ui.base.BaseActivity;
import com.info.lin.infoproject.ui.fragment.GirlFragment;
import com.info.lin.infoproject.ui.fragment.LikeFragment;
import com.info.lin.infoproject.ui.fragment.MainFragment;
import com.info.lin.infoproject.ui.fragment.MeFragment;
import com.info.lin.infoproject.utils.ImgLoadUtils;
import com.info.lin.infoproject.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    private List<Tab> mTabs;
    private TabLayout mTabLayout;
    private NoScrollViewPager mViewPager;
    private List<Fragment> mFragments;

    private ScienceDrawerAdapter mScienceDrawerAdapter;
    private List<ScienceDrawerAdapter.Item> mDrawerItems;
    private ImageView mIvAvatar;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mNavigationRv;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initData();

        initAdapter();
        initView();

        initListener();
    }

    private void initAdapter() {
        Log.d("MainActivity", "mDrawerItems.size():" + mDrawerItems.size());

        mScienceDrawerAdapter = new ScienceDrawerAdapter(R.layout.recycler_navigation_item, mDrawerItems);
    }

    private void initListener() {
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                int position = tab.getPosition();
                if (view != null) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab);
                    TextView textView = (TextView) view.findViewById(R.id.tv_tab);
                    imageView.setImageResource(mTabs.get(position).getImgSrcOn());
                    textView.setTextColor(Color.parseColor("#18d6f0"));
                }
                mViewPager.setCurrentItem(position, false);
                if (position == 0) {
                    setMdIcon(R.drawable.icon_menu);
//                    getActionBarToolbar().setTitle(mTabs.get(position).getTitle());
                    setMdTitle(mTabs.get(position).getTitle());
                } else {
                    getActionBarToolbar().setNavigationIcon(null);
                    setTitle(mTabs.get(position).getTitle());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab);
                    TextView textView = (TextView) view.findViewById(R.id.tv_tab);
                    imageView.setImageResource(mTabs.get(tab.getPosition()).getImgSrcOff());
                    textView.setTextColor(Color.parseColor("#bfbfbf"));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initView() {
        setMdIcon(R.drawable.icon_menu);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationRv = (RecyclerView) findViewById(R.id.rv_navigation);
        mIvAvatar = (ImageView) findViewById(R.id.avatar_navigation);
        mViewPager = (NoScrollViewPager) findViewById(R.id.vp_main);
        mTabLayout = (TabLayout) findViewById(R.id.tab_main);

        for (int i = 0; i < mTabs.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(i)));
        }
        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
//        getActionBarToolbar().setTitle(mTabs.get(0).getTitle());
        setMdTitle(mTabs.get(0).getTitle());

        mNavigationRv.setHasFixedSize(true);
        mNavigationRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mNavigationRv.setAdapter(mScienceDrawerAdapter);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, getActionBarToolbar(), R.string.drawer_open, R.string.drawer_close);

        ImgLoadUtils.loadCircleUrl(this, "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=383177307,616280309&fm=23&gp=0.jpg"
                , mIvAvatar);
    }

    private void initData() {
        mTabs = new ArrayList<>();
        mTabs.add(new Tab("技术资讯", R.drawable.icon_info_on, R.drawable.icon_info_off));
        mTabs.add(new Tab("meizhi", R.drawable.icon_girl_on, R.drawable.icon_girl_off));
        mTabs.add(new Tab("喜欢", R.drawable.icon_like_on, R.drawable.icon_like_off));
        mTabs.add(new Tab("我的", R.drawable.icon_me_on, R.drawable.icon_me_off));

        mFragments = new ArrayList<>();
        mFragments.add(MainFragment.newInstance("xx", "xx"));
        mFragments.add(GirlFragment.newInstance("xx", "xx"));
        mFragments.add(LikeFragment.newInstance("xx", "xx"));
        mFragments.add(MeFragment.newInstance("xx", "xx"));

        mDrawerItems = new ArrayList<>();
        mDrawerItems.add(ScienceDrawerAdapter.Item.PERSONAL);
        mDrawerItems.add(ScienceDrawerAdapter.Item.SWITCH_SKIN);
        mDrawerItems.add(ScienceDrawerAdapter.Item.SWITCH_MODE);
    }

    private View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.design_tab_layout, null, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab);
        TextView textView = (TextView) view.findViewById(R.id.tv_tab);
        Tab tab = mTabs.get(position);
        setTitle(mTabs.get(0).getTitle());
        textView.setText(tab.getTitle());
        if (position == 0) {
            imageView.setImageResource(tab.getImgSrcOn());
            textView.setTextColor(Color.parseColor("#18d6f0"));
        } else {
            imageView.setImageResource(tab.getImgSrcOff());
            textView.setTextColor(Color.parseColor("#bfbfbf"));
        }
        return view;
    }

    class Tab {
        private String title;
        private Integer imgSrcOn;
        private Integer imgSrcOff;

        public Tab(String title, Integer imgSrcOn, Integer imgSrcOff) {
            this.title = title;
            this.imgSrcOn = imgSrcOn;
            this.imgSrcOff = imgSrcOff;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getImgSrcOn() {
            return imgSrcOn;
        }

        public void setImgSrcOn(Integer imgSrcOn) {
            this.imgSrcOn = imgSrcOn;
        }

        public Integer getImgSrcOff() {
            return imgSrcOff;
        }

        public void setImgSrcOff(Integer imgSrcOff) {
            this.imgSrcOff = imgSrcOff;
        }
    }

}
