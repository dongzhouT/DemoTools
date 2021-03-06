package com.tao.demo.rxpermission;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.tao.demo.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.security.Permission;
import java.security.Permissions;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class RxpermissionDemoActivity extends FragmentActivity {
    RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxpermission_demo);
        rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        ToastUtils.showShort("您点击了:" + aBoolean);
                    }
                });
        Observable.just("ffff").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("observable", s);
            }
        });
    }
}
