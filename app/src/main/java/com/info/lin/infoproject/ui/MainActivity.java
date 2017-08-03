package com.info.lin.infoproject.ui;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.info.lin.infoproject.R;
import com.info.lin.infoproject.adapter.DrawerAdapter;
import com.info.lin.infoproject.base.BaseToolbarActivity;
import com.info.lin.infoproject.base.presenter.imp.BasePresenter;
import com.info.lin.infoproject.ui.fragment.MainFragment;
import com.info.lin.infoproject.utils.FragmentUtils;
import com.info.lin.infoproject.utils.ImgLoadUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseToolbarActivity {



    private DrawerAdapter mDrawerAdapter;
    private List<DrawerAdapter.Item> mDrawerItems;
    private ImageView mIvAvatar;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mNavigationRv;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void initAdapter() {
        Log.d("MainActivity", "mDrawerItems.size():" + mDrawerItems.size());
        mDrawerAdapter = new DrawerAdapter(R.layout.recycler_navigation_item, mDrawerItems);
    }

    @Override
    public void initListener() {
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    public void initView() {
        super.initView();
        mToolbar.setTitle("混子是这样的啊");
        mToolbar.setNavigationIcon(R.mipmap.ic_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationRv = (RecyclerView) findViewById(R.id.rv_navigation);
        mIvAvatar = (ImageView) findViewById(R.id.avatar_navigation);

        mNavigationRv.setHasFixedSize(true);
        mNavigationRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mNavigationRv.setAdapter(mDrawerAdapter);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);

        ImgLoadUtils.loadCircleUrl(this, "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=383177307,616280309&fm=23&gp=0.jpg"
                , mIvAvatar);

        FragmentUtils.addFragment(getSupportFragmentManager(), R.id.container_main, MainFragment.newInstance());

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int provideContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        mDrawerItems = new ArrayList<>();
        mDrawerItems.add(DrawerAdapter.Item.PERSONAL);
        mDrawerItems.add(DrawerAdapter.Item.SWITCH_SKIN);
        mDrawerItems.add(DrawerAdapter.Item.SWITCH_MODE);
    }
}
