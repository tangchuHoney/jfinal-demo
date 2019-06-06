package com.jklove.demo.controller;

import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.activerecord.Record;
import com.jklove.demo.service.AdminUserService;

public class AdminUserController extends Controller {

    @Inject
    private AdminUserService userService;
    public void queryUserByName(@Para("name") String userName) {
        Record record = userService.queryUserByUserName(userName);
        render(record.getStr("data"));
    }
    public void queryUser() {
        Record record = userService.queryUser();
        render(record.getStr("data"));
    }
}
