package com.info.lin.infoproject.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.info.lin.infoproject.R;
import com.info.lin.infoproject.adapter.MainPagerAdapter;
import com.info.lin.infoproject.ui.base.BaseActivity;
import com.info.lin.infoproject.ui.fragment.GirlFragment;
import com.info.lin.infoproject.ui.fragment.MainFragment;
import com.info.lin.infoproject.ui.fragment.LikeFragment;
import com.info.lin.infoproject.ui.fragment.MeFragment;
import com.info.lin.infoproject.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    private List<Tab> mTabs;
    private TabLayout mTabLayout;
    private NoScrollViewPager mViewPager;
    private List<Fragment> mFragments;
    private RelativeLayout mTitleGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initData();

        initView();

        initListener();
    }

    private void initListener() {
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
                setTitle(mTabs.get(position).getTitle());
                if (position == 0) {
                    mTitleGroup.setVisibility(View.GONE);
                } else {
                    mTitleGroup.setVisibility(View.VISIBLE);
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
        mViewPager = (NoScrollViewPager) findViewById(R.id.vp_main);
        mTabLayout = (TabLayout) findViewById(R.id.tab_main);
        mTitleGroup = (RelativeLayout) findViewById(R.id.title_group);

        for (int i = 0; i < mTabs.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(i)));
        }
        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTitleGroup.setVisibility(View.GONE);
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
