package com.info.lin.infoproject.ui.fragment;

import com.info.lin.infoproject.ui.base.MainBaseFragment;
import com.info.lin.infoproject.utils.constant.Constants;

/**
 * Created by lin on 2017/2/23.
 */

public class IosFragment extends MainBaseFragment {

    public IosFragment() {
    }

    @Override
    public String getFragmentType() {
        return Constants.TYPE_IOS;
    }

    @Override
    protected boolean canLoadMore() {
        return true;
    }

    @Override
    protected boolean canRefresh() {
        return true;
    }


    public static IosFragment newInstance() {
        return new IosFragment();
    }
}
