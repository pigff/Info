package com.info.lin.infoproject.data.net;

/**
 * Created by lin on 2017/2/22.
 */
public class GankBaseBean<T> {

    private boolean error;

    private T t;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "GankBaseBean{" +
                "t=" + t +
                ", error=" + error +
                '}';
    }
}
