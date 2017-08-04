package com.info.lin.infoproject.ui.fragment;

import com.info.lin.infoproject.ui.base.MainBaseFragment;
import com.info.lin.infoproject.utils.constant.Constants;

/**
 * Created by lin on 2017/2/23.
 */

public class WebFragment extends MainBaseFragment {

    public WebFragment() {
    }

    @Override
    public String getFragmentType() {
        return Constants.TYPE_WEB;
    }

    public static WebFragment newInstance() {
        return new WebFragment();
    }
}
