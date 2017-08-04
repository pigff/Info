package com.info.lin.infoproject.ui.fragment;

import com.info.lin.infoproject.ui.base.MainBaseFragment;
import com.info.lin.infoproject.utils.constant.Constants;

/**
 * Created by lin on 2017/2/23.
 */

public class DailyFragment extends MainBaseFragment {

    public DailyFragment() {
    }

    @Override
    public String getFragmentType() {
        return Constants.TYPE_DAILY;
    }

    @Override
    protected boolean canLoadMore() {
        return true;
    }

    @Override
    protected boolean canRefresh() {
        return true;
    }



    public static DailyFragment newInstance() {
        return new DailyFragment();
    }

}
