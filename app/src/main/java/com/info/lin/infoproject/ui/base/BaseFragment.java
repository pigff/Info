package com.info.lin.infoproject.ui.base;

import android.support.v4.app.Fragment;

import rx.Subscription;

/**
 * Created by lin on 2017/2/21.
 */
public class BaseFragment extends Fragment {

    protected Subscription mSubscription;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
