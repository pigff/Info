package com.info.lin.infoproject.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.info.lin.infoproject.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by long on 2016/8/23.
 * 加载、空视图
 */
public class EmptyLayout extends FrameLayout implements View.OnClickListener {

    public static final String TAG_EMPTY = "empty";
    public static final String TAG_ERROR = "error";

    public static final int STATUS_HIDE = 1001;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_NO_NET = 2;
    public static final int STATUS_NO_DATA = 3;
    private Context mContext;
    private OnBaseLayoutClickListener mOnBaseLayoutClickListener;
    private int mEmptyStatus = STATUS_HIDE;
    private int mBgColor;

    private FrameLayout mEmptyLayout;
    private MaterialDesignProgressBar mProgressBar;
    private ImageView mNetErrorIv;
    private ImageView mNoDataIv;

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    /**
     * 初始化
     */
    private void init(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.EmptyLayout);
        try {
            mBgColor = a.getColor(R.styleable.EmptyLayout_background_color, Color.WHITE);
        } finally {
            a.recycle();
        }
        View.inflate(mContext, R.layout.layout_empty_loading, this);
        mNetErrorIv = (ImageView) findViewById(R.id.iv_net_error);
        mNoDataIv = (ImageView) findViewById(R.id.iv_no_data);
        mEmptyLayout = (FrameLayout) findViewById(R.id.empty_layout);
        mProgressBar = (MaterialDesignProgressBar) findViewById(R.id.empty_loading);

        mNoDataIv.setTag(TAG_EMPTY);
        mNetErrorIv.setTag(TAG_ERROR);

        mNoDataIv.setOnClickListener(this);
        mNetErrorIv.setOnClickListener(this);
        mEmptyLayout.setBackgroundColor(mBgColor);
        _switchEmptyView();
    }

    /**
     * 隐藏视图
     */
    public void hide() {
        mEmptyStatus = STATUS_HIDE;
        _switchEmptyView();
    }

    /**
     * 设置状态
     *
     * @param emptyStatus
     */
    public void setEmptyStatus(@EmptyStatus int emptyStatus) {
        mEmptyStatus = emptyStatus;
        _switchEmptyView();
    }

    /**
     * 获取状态
     *
     * @return 状态
     */
    public int getEmptyStatus() {
        return mEmptyStatus;
    }


//    /**
//     * 设置图标
//     * @param resId 资源ID
//     */
//    public void setEmptyIcon(int resId) {
//        mIvEmptyIcon.setImageResource(resId);
//    }
//
//    /**
//     * 设置图标
//     * @param drawable drawable
//     */
//    public void setEmptyIcon(Drawable drawable) {
//        mIvEmptyIcon.setImageDrawable(drawable);
//    }


    /**
     * 切换视图
     */
    private void _switchEmptyView() {
        switch (mEmptyStatus) {
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                mNoDataIv.setVisibility(GONE);
                mNetErrorIv.setVisibility(GONE);
                mProgressBar.setVisibility(VISIBLE);
                break;
            case STATUS_NO_DATA:
                setVisibility(VISIBLE);
                mNoDataIv.setVisibility(VISIBLE);
                mNetErrorIv.setVisibility(GONE);
                mProgressBar.setVisibility(GONE);
                break;
            case STATUS_NO_NET:
                setVisibility(VISIBLE);
                mProgressBar.setVisibility(GONE);
                mNoDataIv.setVisibility(GONE);
                mNetErrorIv.setVisibility(VISIBLE);
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                break;
        }
    }

    /**
     * 设置重试监听器
     *
     * @param
     */
    public void setOnBaseLayoutClickListener(OnBaseLayoutClickListener onBaseLayoutClickListener) {
        this.mOnBaseLayoutClickListener = onBaseLayoutClickListener;
    }

    @Override
    public void onClick(View v) {
        //防止多次点击
        ViewUtils.setDelayedClickable(v, 500);
        if (mOnBaseLayoutClickListener != null) {
            Object object = v.getTag();
            if (null != object) {
                String tag = v.getTag().toString();
                if (TAG_EMPTY.equals(tag)) {
                    mOnBaseLayoutClickListener.onClickEmpty();
                }
                if (TAG_ERROR.equals(tag)) {
                    mOnBaseLayoutClickListener.onClickRetry();
                }
            }
        }
    }


    /**
     * 依次表示网络错误点击事件
     * <p>
     * 空页面点击事件
     */
    public interface OnBaseLayoutClickListener {
        void onClickRetry();

        void onClickEmpty();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_NO_NET, STATUS_NO_DATA})
    public @interface EmptyStatus {
    }
}