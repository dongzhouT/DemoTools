package com.tao.demo.mvpDemo.loginDemo;

import android.accounts.NetworkErrorException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author taodzh
 * create at 2019/5/22
 */
public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {
    abstract void onSuccess(T bean);

    abstract void onFail(String code, String msg);

    abstract void onNetError(Throwable e);

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseEntity<T> tBaseEntity) {
        if (tBaseEntity.ok()) {
            onSuccess(tBaseEntity.data);
        } else {
            onFail(tBaseEntity.retCode, tBaseEntity.retInfo);
        }

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ConnectException
                || e instanceof TimeoutException
                || e instanceof NetworkErrorException
                || e instanceof UnknownHostException) {
            onNetError(e);
        } else {
            e.printStackTrace();
        }

    }

    @Override
    public void onComplete() {

    }
}
