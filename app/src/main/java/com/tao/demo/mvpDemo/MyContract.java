package com.tao.demo.mvpDemo;

/**
 * created by taodzh on 2019/4/29
 */
public interface MyContract {
    interface IView extends BaseView<IMyPresent> {
        void setViewText(String text);
    }

    interface IMyPresent extends BasePresent {
        void sendData();
    }
}
