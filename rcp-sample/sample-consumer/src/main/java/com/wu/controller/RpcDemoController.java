package com.wu.controller;


import com.wu.annotation.RCPReference;
import com.wu.api.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class RpcDemoController {

    @RCPReference
    UserService userService;

    public void getUserInfo(){
        String info = userService.getUserInfoById(20L);
        System.out.println(info);
    }
}
