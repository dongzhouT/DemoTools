package com.tao.demo.mvpDemo;

import android.content.Context;

/**
 * created by taodzh on 2019/4/29
 */
public class MyPresent implements MyContract.IMyPresent {
    private Context context;
    private MyContract.IView view;

    public MyPresent(Context context, MyContract.IView view) {
        this.context = context;
        this.view = view;
        this.view.setPresent(this);
    }

    @Override
    public void sendData() {
        this.view.setViewText("这是发送数据");
    }
}
