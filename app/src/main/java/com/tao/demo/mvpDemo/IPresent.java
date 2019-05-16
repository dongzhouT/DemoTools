package com.tao.demo.mvpDemo;

import android.content.Context;

/**
 * created by taodzh on 2019/4/29
 */
public class IPresent<T extends BaseView> implements BasePresent {
    public Context context;
    public T view;

    public IPresent(Context context, T view) {
        this.context = context;
        this.view = view;
        this.view.setPresent(this);
    }
}
