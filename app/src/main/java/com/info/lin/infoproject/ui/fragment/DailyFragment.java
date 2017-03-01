package com.info.lin.infoproject.ui.fragment;

import com.info.lin.infoproject.ui.base.MainBaseFragment;
import com.info.lin.infoproject.utils.Constants;

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

    public static DailyFragment newInstance() {
        return new DailyFragment();
    }

}
