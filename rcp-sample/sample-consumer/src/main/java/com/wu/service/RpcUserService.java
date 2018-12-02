package com.wu.service;

import com.wu.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RpcUserService {

    @Autowired
    UserService userService;

    public String getUserInfo(){
        return userService.getUserInfoById(33L);
    }
}
