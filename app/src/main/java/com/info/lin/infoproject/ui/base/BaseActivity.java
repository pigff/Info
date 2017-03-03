package com.info.lin.infoproject.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.info.lin.infoproject.R;

import rx.Subscription;

/**
 * Created by lin on 2017/2/21.
 */
public class BaseActivity extends AppCompatActivity {

    protected Subscription mSubscription;

    private Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setMdIcon(Integer res) {
        if (getActionBarToolbar() == null) {
            return;
        }
        mActionBarToolbar.setNavigationIcon(res);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setMdTitle(String title) {
        if (getActionBarToolbar() == null) {
            return;
        }
        mActionBarToolbar.setTitle(title);
        setTitleGone();
    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
        return mActionBarToolbar;
    }

    public void setTitle(String title) {
        if (getActionBarToolbar() == null) {
            return;
        }
        if (mActionBarToolbar != null) {
            ((TextView) mActionBarToolbar.findViewById(R.id.title_toolbar)).setText(title);
            ((TextView) mActionBarToolbar.findViewById(R.id.title_toolbar)).setVisibility(View.VISIBLE);
            mActionBarToolbar.setTitle(null);
        }
    }

    public void setTitleGone() {
        if (getActionBarToolbar() == null) {
            return;
        }
        if (mActionBarToolbar != null) {
            ((TextView) mActionBarToolbar.findViewById(R.id.title_toolbar)).setVisibility(View.GONE);
        }
    }

    public void setTitltColor(int color) {
        if (mActionBarToolbar != null) {
            ((TextView) mActionBarToolbar.findViewById(R.id.title_toolbar)).setTextColor(color);
        }
    }

    public void setTitle(@StringRes int title) {
        if (getActionBarToolbar() == null) {
            return;
        }
        if (mActionBarToolbar != null) {
            ((TextView) mActionBarToolbar.findViewById(R.id.title_toolbar)).setText(title);
            mActionBarToolbar.setTitle(null);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
