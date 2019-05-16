package com.tao.demo.okhttp;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * created by taodzh on 2019/4/28
 */
public interface Interceptor {
    Response intercept(Chain chain);
    interface Chain {
        Response proceed(Request request);
    }
}
