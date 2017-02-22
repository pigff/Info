package com.info.lin.infoproject.network;

/**
 * Created by lin on 2017/2/22.
 */
public abstract class CallBack<T> {

    public void handle(T t) {
        success(t);
    }

    public void handleError() {
        error();
    }

    public abstract void success(T t);

    public abstract void error();
}
