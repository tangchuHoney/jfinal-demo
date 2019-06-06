package com.jklove.demo.router;

import com.jfinal.config.Routes;
import com.jklove.demo.controller.AdminUserController;
import com.jklove.demo.interceptor.AdminInterceptor;

public class AdminRouter extends Routes {
    @Override
    public void config() {
        addInterceptor(new AdminInterceptor());
        add("/sytesm/user", AdminUserController.class);
    }
}