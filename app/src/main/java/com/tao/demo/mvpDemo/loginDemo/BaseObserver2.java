package com.tao.demo.mvpDemo.loginDemo;

import com.tao.demo.mvpDemo.loginDemo.entity.BaseResultEntity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author taodzh
 * create at 2019/5/23
 */
public abstract class BaseObserver2<T extends BaseResultEntity> implements Observer<T> {
    abstract void onSuccess(T bean);

    abstract void onFail(String code, String msg);

    abstract void onNetError(Throwable e);

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if (t.ok()) {
            onSuccess(t);
        } else {
            onFail(t.retCode, t.retInfo);
        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
