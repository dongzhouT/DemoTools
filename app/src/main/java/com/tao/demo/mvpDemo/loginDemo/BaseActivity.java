package com.tao.demo.mvpDemo.loginDemo;



import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.androidannotations.annotations.EActivity;

import io.reactivex.ObservableTransformer;

/**
 * @author taodzh
 * create at 2019/5/22
 */
@EActivity
public class BaseActivity extends RxAppCompatActivity implements IBaseView {
    @Override
    public <T> ObservableTransformer<T, T> bindLifeCycle() {
        return bindToLifecycle();
    }
}
