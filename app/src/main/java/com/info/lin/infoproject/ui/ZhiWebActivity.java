package com.info.lin.infoproject.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.info.lin.infoproject.R;
import com.info.lin.infoproject.data.net.ZhiDetailResponse;
import com.info.lin.infoproject.network.NormalCallBack;
import com.info.lin.infoproject.network.RequestManager;
import com.info.lin.infoproject.utils.Constants;
import com.info.lin.infoproject.utils.HtmlUtil;
import com.info.lin.infoproject.utils.ImgLoadUtils;

public class ZhiWebActivity extends AppCompatActivity {

    private int mStoryId;
    private ImageView mDetailBarImg;
    private TextView mDetailBarTitle;
    private TextView mDetailBarCopyright;
    private FrameLayout mContainer;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_web);
        init();
        getData();
    }

    private void getData() {
        RequestManager.getInstance().getZhiDetailedStory(mStoryId, new NormalCallBack<ZhiDetailResponse>() {
            @Override
            public void success(ZhiDetailResponse zhiDetailResponse) {
                mDetailBarTitle.setText(zhiDetailResponse.getTitle());
                mDetailBarCopyright.setText(zhiDetailResponse.getImage_source());
                String htmlData = HtmlUtil.createHtmlData(zhiDetailResponse.getBody(), zhiDetailResponse.getCss(), zhiDetailResponse.getJs());
                mWebView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
                ImgLoadUtils.loadUrl(ZhiWebActivity.this, zhiDetailResponse.getImage(), mDetailBarImg);
            }

            @Override
            public void error() {

            }
        });
    }

    private void init() {
        initData();
        initView();
    }

    private void initView() {
        initToolbar();
        mDetailBarImg = (ImageView) findViewById(R.id.detail_bar_image);
        mDetailBarTitle = (TextView) findViewById(R.id.detail_bar_title);
        mDetailBarCopyright = (TextView) findViewById(R.id.detail_bar_copyright);
        mContainer = (FrameLayout) findViewById(R.id.zhi_web_container);

        initWebView();
    }

    private void initWebView() {
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mContainer.addView(mWebView);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        mStoryId = getIntent().getIntExtra(Constants.DATA_INTENT, -1);
        if (mStoryId == -1) {
            finish();
        }
    }
}
