package com.info.lin.infoproject.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.info.lin.infoproject.R;
import com.info.lin.infoproject.base.BaseToolbarActivity;
import com.info.lin.infoproject.base.presenter.imp.BasePresenter;
import com.info.lin.infoproject.ui.fragment.GirlFragment;
import com.info.lin.infoproject.ui.fragment.LikeFragment;
import com.info.lin.infoproject.ui.fragment.MainFragment;
import com.info.lin.infoproject.ui.fragment.ZhiFragment;
import com.info.lin.infoproject.utils.FragmentUtils;
import com.info.lin.infoproject.utils.ImgLoadUtils;

public class MainActivity extends BaseToolbarActivity {

    private ImageView mIvAvatar;
    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;


    @Override
    public void initListener() {
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initView() {
        super.initView();
        mToolbar.setTitle("干货gank.io");
        mToolbar.setNavigationIcon(R.mipmap.ic_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        //设置选中的项
//        mNavigationView.setCheckedItem(R.id.nav_call);
        //设置菜单项的点击事件
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_meizhi:
                        FragmentUtils.addFragment(getSupportFragmentManager(), GirlFragment.newInstance()
                                , R.id.container_main, false, true);
                        break;
                    case R.id.nav_zhihu:
                        FragmentUtils.addFragment(getSupportFragmentManager(), ZhiFragment.newInstance()
                                , R.id.container_main, false, true);
                        break;
                    case R.id.nav_like:
                        FragmentUtils.addFragment(getSupportFragmentManager(), LikeFragment.newInstance()
                                , R.id.container_main, false, true);
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
        mIvAvatar = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.avatar_navigation);
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

}
