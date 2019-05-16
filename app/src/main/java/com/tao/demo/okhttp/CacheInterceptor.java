package com.tao.demo.okhttp;

import okhttp3.Response;

/**
 * created by taodzh on 2019/4/28
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain)  {
        System.out.println("CacheInterceptor intercept()");
        return chain.proceed(null);
    }
}
