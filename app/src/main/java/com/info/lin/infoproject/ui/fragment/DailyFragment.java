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
    public boolean getCanLoadMore() {
        return false;
    }

    public static DailyFragment newInstance() {
        return new DailyFragment();
    }

}
