package com.wu.api;

import org.springframework.stereotype.Component;

@Component
public interface UserService {

    public String getUserInfoById(Long id);

}
