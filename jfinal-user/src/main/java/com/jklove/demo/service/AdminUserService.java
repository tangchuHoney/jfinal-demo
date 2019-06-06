package com.jklove.demo.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class AdminUserService {
    public Record queryUserByUserName(String userName) {
        Record record = new Record();
        record.set("data", Db.find(Db.getSqlPara("admin.user.queryByUserName", Kv.by("user_name", userName))));
        return record;
    }

    public Record queryUser() {
        String sql = "select * from sys_user";
        List<Record> list = Db.find(sql);
        Record record = new Record();
        record.set("data", list);
        return record;
    }
}
