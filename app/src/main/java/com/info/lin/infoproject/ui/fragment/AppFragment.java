package com.info.lin.infoproject.ui.fragment;

import com.info.lin.infoproject.ui.base.MainBaseFragment;
import com.info.lin.infoproject.utils.constant.Constants;

/**
 * Created by lin on 2017/2/23.
 */

public class AppFragment extends MainBaseFragment {

    public AppFragment() {

    }

    @Override
    public String getFragmentType() {
        return Constants.TYPE_APP;
    }

    @Override
    protected boolean canLoadMore() {
        return true;
    }

    @Override
    protected boolean canRefresh() {
        return true;
    }


    public static AppFragment newInstance() {
        return new AppFragment();
    }
}
