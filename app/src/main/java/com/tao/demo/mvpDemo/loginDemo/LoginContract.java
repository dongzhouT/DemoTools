package com.tao.demo.mvpDemo.loginDemo;

import com.tao.demo.mvpDemo.loginDemo.entity.LoginResultBean;

import io.reactivex.Observable;

/**
 * @author taodzh
 * create at 2019/5/22
 */
public class LoginContract {
    interface View extends IBaseView {
        String getUserName();

        String getPassword();

        void success(String token);

        void fail(String code, String msg);

        void showLoading();

        void closeLoading();
    }

    interface Presenter {
        void login();
    }

    interface Model {
        void login(String name, String pass, BaseCallBack<UserInfoResultBean> callBack);

        void login2(String name, String pass, BaseObserver<UserInfoResultBean> callBack);

        Observable<BaseEntity<UserInfoResultBean>> login3(String name, String pass);

        Observable<LoginResultBean> login4(String name, String pass);
    }
}
