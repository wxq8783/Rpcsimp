package com.wu.controller;


import com.wu.annotation.RCPReference;
import com.wu.api.UserService;
import com.wu.service.RpcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rpc")
public class RpcDemoController {


    @Autowired
    RpcUserService rpcUserService;

    @RequestMapping("/user")
    @ResponseBody
    public String getUserInfo(){
        String info = rpcUserService.getUserInfo();
        System.out.println(info);
        return info;
    }
}
