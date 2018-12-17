package com.wu.service;

import com.wu.annotation.RCPReference;
import com.wu.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RpcUserService {

    @RCPReference
    UserService userService;

    public String getUserInfo(){
        return "";
    }
}
