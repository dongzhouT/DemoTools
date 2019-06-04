package com.tao.demo.mvpDemo.loginDemo;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.tao.demo.R;
import com.tao.demo.utils.Md5Utils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements LoginContract.View {
    LoginContract.Presenter mPresenter;
    @ViewById(R.id.edt_name)
    EditText edtName;
    @ViewById(R.id.edt_pass)
    EditText edtPass;
    @ViewById(R.id.tv_log)
    TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new LoginPresenter(this);
    }

    @Override
    public String getUserName() {
        return edtName.getText().toString();
    }

    @Override
    public String getPassword() {
        return Md5Utils.encode(edtPass.getText().toString());
    }

    @Override
    public void success(String token) {
        ToastUtils.showShort("登录成功：" + token);
        tvLog.setText(token);
    }

    @Override
    public void fail(String code, String msg) {
        ToastUtils.showShort("发生错误：" + msg);
        tvLog.setText(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Click(R.id.btn_login)
    void onClickLogin() {
        mPresenter.login();
    }
}
