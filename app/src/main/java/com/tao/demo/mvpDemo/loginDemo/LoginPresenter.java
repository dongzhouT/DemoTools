package com.tao.demo.mvpDemo.loginDemo;

import com.tao.demo.mvpDemo.loginDemo.entity.LoginResultBean;
import com.tao.demo.net.RxSchedulers;

import java.util.concurrent.TimeUnit;

/**
 * @author taodzh
 * create at 2019/5/22
 */
public class LoginPresenter implements LoginContract.Presenter {
    LoginContract.View mView;
    LoginContract.Model mModel;

    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
        mModel = new LoginModel();
    }

    @Override
    public void login() {
        mModel.login4(mView.getUserName(), mView.getPassword())
                .delay(3000, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.applySchedulers(mView))
//                .compose(mView.bindLifeCycle())
//                .compose(RxSchedulers.applySchedulers())
                .subscribe(new BaseObserver2<LoginResultBean>() {
                    @Override
                    void onSuccess(LoginResultBean bean) {
                        String token = bean.data.accessToken;
                        mView.success(token);
                    }

                    @Override
                    void onFail(String code, String msg) {
                        mView.fail(code, msg);
                    }

                    @Override
                    void onNetError(Throwable e) {

                    }
                });
//        mModel.login3(mView.getUserName(), mView.getPassword())
//                .compose(mView.<BaseEntity<UserInfoResultBean>>bindLifeCycle())
//                .subscribe(new BaseObserver<UserInfoResultBean>() {
//                    @Override
//                    void onSuccess(UserInfoResultBean bean) {
//
//                    }
//
//                    @Override
//                    void onFail(String code, String msg) {
//
//                    }
//
//                    @Override
//                    void onNetError(Throwable e) {
//
//                    }
//                });
        //无法绑定rxlifecycle
//        mModel.login2(mView.getUserName(), mView.getPassword(), new BaseObserver<UserInfoResultBean>() {
//            @Override
//            public void onSuccess(UserInfoResultBean bean) {
//                mView.success(bean.accessToken);
//            }
//
//            @Override
//            public void onFail(String code, String msg) {
//                mView.fail(code, msg);
//            }
//
//            @Override
//            public void onNetError(Throwable e) {
//            }
//        });
    }
}
