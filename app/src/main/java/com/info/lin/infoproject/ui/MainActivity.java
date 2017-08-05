package com.info.lin.infoproject.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
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
import com.info.lin.infoproject.utils.LogUtil;

public class MainActivity extends BaseToolbarActivity {

    private String[] mFragmentNames = {ZhiFragment.class.getName(), GirlFragment.class.getName(), LikeFragment.class.getName()};
    private ImageView mIvAvatar;
    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private Fragment mDestFragment;


    @Override
    public void initListener() {
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    protected void initData() {
        mDestFragment = null;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("干货gank.io");
        mToolbar.setNavigationIcon(R.mipmap.ic_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        //设置选中的项
        mNavigationView.setCheckedItem(R.id.nav_main);
        //设置菜单项的点击事件
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment srcFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_meizhi:
                        srcFragment = GirlFragment.newInstance();
                        break;
                    case R.id.nav_zhihu:
                        srcFragment = ZhiFragment.newInstance();
                        break;
                    case R.id.nav_like:
                        srcFragment = LikeFragment.newInstance();
                        break;
                    case R.id.nav_main:
                        setTitle("干货gank.io");
                        mNavigationView.setCheckedItem(R.id.nav_main);
                        getSupportFragmentManager().popBackStackImmediate();
                        break;
                    default:
                        break;
                }
                if (srcFragment != null) {
                    addFragment(srcFragment);
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

    private void addFragment(Fragment srcFragment) {
        // 获取当前回退栈中的Fragment个数
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 判断当前回退栈中的fragment个数,
        if (fragmentManager.getBackStackEntryCount() > 0) {
            // 立即回退一步
//            fragmentManager.popBackStackImmediate();
            // 获取当前退到了哪一个Fragment上,重新获取当前的Fragment回退栈中的个数
            FragmentManager.BackStackEntry backStack = fragmentManager
                    .getBackStackEntryAt(fragmentManager
                            .getBackStackEntryCount() - 1);
            // 获取当前栈顶的Fragment的标记值
            String tag = backStack.getName();
            LogUtil.d(TAG, "tag: " + tag);
            // 判断当前是哪一个标记
            if (srcFragment.getClass().getName().equals(tag)) {
                // 设置首页选中
                return;

            }
            fragmentManager.popBackStackImmediate();
            FragmentUtils.addFragment(getSupportFragmentManager(), srcFragment
                    , R.id.container_main, false, true);
            LogUtil.d(TAG, "class name" + srcFragment.getClass().getName());
        } else {
            FragmentUtils.addFragment(getSupportFragmentManager(), srcFragment
                    , R.id.container_main, false, true);
        }

        if (TextUtils.equals(srcFragment.getClass().getName(), mFragmentNames[0])) {
            setTitle("知乎");
            mNavigationView.setCheckedItem(R.id.nav_zhihu);
        } else if (TextUtils.equals(srcFragment.getClass().getName(), mFragmentNames[1])) {
            setTitle("妹纸");
            mNavigationView.setCheckedItem(R.id.nav_meizhi);
        } else if (TextUtils.equals(srcFragment.getClass().getName(), mFragmentNames[2])) {
            setTitle("喜欢");
            mNavigationView.setCheckedItem(R.id.nav_like);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            setTitle("干货gank.io");
            getSupportFragmentManager().popBackStackImmediate();
            mNavigationView.setCheckedItem(R.id.nav_main);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void setTitle(String title) {
        mToolbar.setTitle(title);
    }
}