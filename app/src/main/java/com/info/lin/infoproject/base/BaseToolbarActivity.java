package com.info.lin.infoproject.base;

import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.info.lin.infoproject.App;
import com.info.lin.infoproject.R;
import com.info.lin.infoproject.base.presenter.imp.BasePresenter;

/**
 * Created by greedy on 17/3/11.
 */

public abstract class BaseToolbarActivity<P extends BasePresenter> extends MvpActivity<P> {

    protected AppBarLayout mAppBarLayout;
    protected Toolbar mToolbar;
    protected TextView mToolbarTitle;
    protected ImageView mRightIv;
    protected TextView mRightTv;
    protected ImageView mLeftIv;
    private LinearLayout mRightLayout;


    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.title_toolbar);
        mRightIv = (ImageView) findViewById(R.id.right_iv_toolbar);
        mRightTv = (TextView) findViewById(R.id.right_tv_toolbar);
        mLeftIv = (ImageView) findViewById(R.id.left_iv_toolbar);
        mRightLayout = (LinearLayout) findViewById(R.id.layout_right_toolbar);
        if (mToolbar == null || mToolbarTitle == null
                || mRightIv == null || mRightTv == null || mLeftIv == null || mRightLayout == null) {
            throw new IllegalStateException(
                    "The subclass of ToolbarActivity must contain a toolbar.");
        }

        if (canGoBack()) {
            mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected void  setTitle(String title) {
        mToolbarTitle.setText(title);
    }

    protected void setLeftIv(int imgSrc, @Nullable View.OnClickListener onClickListener) {
        mLeftIv.setVisibility(View.VISIBLE);
        mLeftIv.setImageResource(imgSrc);
        if (onClickListener != null) {
            mLeftIv.setOnClickListener(onClickListener);
        } else {
            mLeftIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected void setRightIv(int imgSrc, View.OnClickListener onClickListener) {
        mRightIv.setVisibility(View.VISIBLE);
        mRightIv.setImageResource(imgSrc);
        mRightLayout.setOnClickListener(onClickListener);
    }

    protected void setRightTv(String text, View.OnClickListener onClickListener) {
        mRightTv.setVisibility(View.VISIBLE);
        mRightTv.setText(text);
        mRightLayout.setOnClickListener(onClickListener);
    }

    protected void setRightTv(String text, int textBack, int textColor, View.OnClickListener onClickListener) {
        mRightTv.setVisibility(View.VISIBLE);
        mRightTv.setText(text);
        mRightTv.setBackground(ContextCompat.getDrawable(App.getInstance(), textBack));
        mRightTv.setTextColor(ContextCompat.getColor(App.getInstance(), textColor));
        mRightLayout.setOnClickListener(onClickListener);
    }

    protected void setRightLayout(String text, int src, View.OnClickListener onClickListener) {
        mRightTv.setText(text);
        mRightIv.setImageResource(src);
        mRightLayout.setOnClickListener(onClickListener);
    }


    protected boolean canGoBack() {
        return true;
    }


    @Override
    protected boolean hasBaseLayout() {
        return false;
    }
}
