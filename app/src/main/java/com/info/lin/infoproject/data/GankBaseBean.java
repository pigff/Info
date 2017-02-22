package com.info.lin.infoproject.data;

/**
 * Created by lin on 2017/2/22.
 */
public class GankBaseBean<T> {

    protected boolean error;

    protected T t;

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
}
