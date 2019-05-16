package com.tao.demo.okhttp;

import okhttp3.Response;

/**
 * created by taodzh on 2019/4/28
 */
public class ConnectionInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) {
        System.out.println("ConnectionInterceptor intercept()");
        return new Response.Builder().build();
    }
}
