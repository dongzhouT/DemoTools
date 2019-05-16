package com.tao.demo.okhttp;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * created by taodzh on 2019/4/28
 */
public class RealInterceptorChain implements Interceptor.Chain {
    List<Interceptor> interceptors = new ArrayList<>();
    private final int index;

    public RealInterceptorChain(List<Interceptor> interceptors, int index) {
        this.interceptors = interceptors;
        this.index = index;
    }

    @Override
    public Response proceed(Request request) {
        if (index >= interceptors.size()) throw new AssertionError();
        RealInterceptorChain next = new RealInterceptorChain(interceptors, index + 1);
        Interceptor interceptor = interceptors.get(index);
        Response response = interceptor.intercept(next);
        return response;
    }

}
