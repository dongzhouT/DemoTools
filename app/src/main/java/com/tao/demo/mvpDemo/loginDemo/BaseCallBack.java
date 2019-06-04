package com.tao.demo.mvpDemo.loginDemo;

/**
 * @author taodzh
 * create at 2019/5/22
 */
@Deprecated
public interface BaseCallBack<T> {
    void onSuccess(T bean);

    void onFail(String code, String msg);

    void onNetError(Throwable e);
}
