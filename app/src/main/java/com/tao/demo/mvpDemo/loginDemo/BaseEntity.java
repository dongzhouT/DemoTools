package com.tao.demo.mvpDemo.loginDemo;

/**
 * @author taodzh
 * create at 2019/5/22
 */
public class BaseEntity<T> {
    public String retCode;
    public String retInfo;
    public T data;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(String retInfo) {
        this.retInfo = retInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean ok() {
        return "000000".equals(getRetCode());
    }
}
