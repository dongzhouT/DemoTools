package com.tao.demo.mvpDemo.loginDemo.entity;

/**
 * @author taodzh
 * create at 2019/5/23
 */
public class LoginResultBean extends BaseResultEntity {
    public Data data;

    public class Data {
        public String accessToken;
    }
}
