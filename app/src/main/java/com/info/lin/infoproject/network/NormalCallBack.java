package com.info.lin.infoproject.network;

/**
 * Created by lin on 2017/3/7.
 */

public abstract class NormalCallBack<T> {

    public void handle(T t) {
        success(t);
    }

    public void handleError() {
        error();
    }

    public abstract void success(T t);

    public abstract void error();
}
