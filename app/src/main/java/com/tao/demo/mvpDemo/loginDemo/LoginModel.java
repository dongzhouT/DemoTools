package com.tao.demo.mvpDemo.loginDemo;

import com.tao.demo.mvpDemo.loginDemo.entity.LoginResultBean;
import com.tao.demo.net.RetrofitManager;
import com.tao.demo.net.RxSchedulers;

import io.reactivex.Observable;

/**
 * @author taodzh
 * create at 2019/5/22
 */
public class LoginModel implements LoginContract.Model {
    @Override
    public void login(String name, String pass, final BaseCallBack<UserInfoResultBean> callBack) {
        RetrofitManager.getApiService().login2(name, pass)
                .compose(RxSchedulers.<BaseEntity<UserInfoResultBean>>applySchedulers())
                .subscribe(new BaseObserver<UserInfoResultBean>() {
                    @Override
                    void onSuccess(UserInfoResultBean bean) {
                        callBack.onSuccess(bean);
                    }

                    @Override
                    void onFail(String code, String msg) {
                        callBack.onFail(code, msg);
                    }

                    @Override
                    void onNetError(Throwable e) {
                        callBack.onNetError(e);
                    }
                });
    }

    @Override
    public void login2(String name, String pass, BaseObserver<UserInfoResultBean> callback) {
        RetrofitManager.getApiService().login2(name, pass)
                .compose(RxSchedulers.<BaseEntity<UserInfoResultBean>>applySchedulers())
                .subscribe(callback);
    }

    /**
     * @param name
     * @param pass
     * @return 返回Observable 可以用rxLifecycle
     */
    @Override
    public Observable<BaseEntity<UserInfoResultBean>> login3(String name, String pass) {
        return RetrofitManager.getApiService().login2(name, pass)
                .compose(RxSchedulers.<BaseEntity<UserInfoResultBean>>applySchedulers());
    }

    /**
     * @param name
     * @param pass
     * @return 返回Observable 可以用rxLifecycle
     * 返回参数不用泛型，用继承
     */
    @Override
    public Observable<LoginResultBean> login4(String name, String pass) {
        return RetrofitManager.getApiService().login4(name, pass)
                .compose(RxSchedulers.<LoginResultBean>applySchedulers());
    }

}
