package com.tao.demo.okhttp;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * created by taodzh on 2019/4/28
 */
public class Main {
    public static void main(String[] params) {
        System.out.println("start===========");
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new CacheInterceptor());
        interceptors.add(new ConnectionInterceptor());
        RealInterceptorChain chain = new RealInterceptorChain(interceptors, 0);
        Response response = chain.proceed(null);
        System.out.println("end===========");
        System.out.println(response);
    }
}
