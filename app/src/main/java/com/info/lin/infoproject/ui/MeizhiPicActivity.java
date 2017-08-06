package com.info.lin.infoproject.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.info.lin.infoproject.R;
import com.info.lin.infoproject.base.BaseActivity;
import com.info.lin.infoproject.utils.BarUtils;
import com.info.lin.infoproject.utils.constant.Constants;

import uk.co.senab.photoview.PhotoView;

public class MeizhiPicActivity extends BaseActivity {

    public static final String TRANSIT_PIC = "picture";
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizhi_pic);
        init();
    }

    private void init() {
        initData();
        initView();
    }

    private void initView() {

        BarUtils.setColor(this, Color.BLACK);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        PhotoView photoView = (PhotoView) findViewById(R.id.iv_pic_meizhi);
//        ViewCompat.setTransitionName(photoView, TRANSIT_PIC);
        Glide.with(this)
                .load(mUrl)
                .placeholder(Color.BLACK)
                .dontAnimate()
                .into(photoView);
        toolbar.setBackgroundColor(Color.BLACK);
        appBarLayout.setAlpha(0.5f);
    }

    private void initData() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(Constants.DATA_INTENT);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.common_part_left_in, R.anim.common_whole_right_out);
    }
}
