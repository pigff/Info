package com.info.lin.infoproject.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.info.lin.infoproject.R;
import com.info.lin.infoproject.ui.base.BaseActivity;
import com.info.lin.infoproject.utils.BarUtils;
import com.info.lin.infoproject.utils.ImgLoadUtils;
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
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_pic);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        PhotoView photoView = (PhotoView) findViewById(R.id.iv_pic_meizhi);
        ViewCompat.setTransitionName(photoView, TRANSIT_PIC);
        ImgLoadUtils.loadUrl(this, mUrl, photoView);
        appBarLayout.setAlpha(0.5f);
    }

    private void initData() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(Constants.DATA_INTENT);
    }
}
