package com.tao.demo.mvpDemo.loginDemo.entity;

/**
 * @author taodzh
 * create at 2019/5/23
 */
public class BaseResultEntity {
    public String retCode;
    public String retInfo;

    public boolean ok() {
        return "000000".equals(retCode);
    }
}
