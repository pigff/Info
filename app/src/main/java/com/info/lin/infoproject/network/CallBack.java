package com.info.lin.infoproject.network;

import com.info.lin.infoproject.data.net.GankBaseBean;

/**
 * Created by lin on 2017/2/22.
 */
public abstract class CallBack<T extends GankBaseBean> {

    public void handle(T t) {
        if (!t.isError()) {
            success(t);
        } else {
            error();
        }
    }

    public void handleError() {
        error();
    }

    public abstract void success(T t);

    public abstract void error();
}
