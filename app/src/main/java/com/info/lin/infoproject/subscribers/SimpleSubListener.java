package com.info.lin.infoproject.subscribers;

/**
 * Created by greedy on 2017/4/6.
 */

public abstract class SimpleSubListener<T> {
    public abstract void onNext(T t);

    public abstract void onError(Throwable throwable);

    public void onComplete(){

    }
}
