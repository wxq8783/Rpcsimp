package com.wu.service;

import com.wu.annotation.RCPReference;
import com.wu.api.UserService;
import com.wu.util.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RpcUserService {

//    @Autowired
//    UserService userService;

    public String getUserInfo(){
        UserService bean = SpringBeanUtil.INSTANCE.getBean("com.wu.api.UserService");
        return bean.getUserInfoById(44L);
    }
}
