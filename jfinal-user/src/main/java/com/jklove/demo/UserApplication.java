package com.jklove.demo;

import com.jfinal.server.undertow.UndertowServer;
import com.jklove.demo.config.JfinalConfig;

public class UserApplication {
    public static void main(String[] args) {
        UndertowServer
                .create(JfinalConfig.class,"config/undertow.txt")
                .start();
    }

}
