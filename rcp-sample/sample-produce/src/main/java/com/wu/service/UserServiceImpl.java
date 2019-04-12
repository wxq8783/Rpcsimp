package com.wu.service;

import com.wu.annotation.RPCService;

import com.wu.api.UserService;
import org.springframework.stereotype.Service;

@RPCService(UserService.class)
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getUserInfoById(Long id) {
        return "wuxq"+id;
    }
}
