package com.jklove.demo.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class AdminInterceptor implements Interceptor {

    public void intercept(Invocation invocation) {
        invocation.invoke();

    }
}