package com.wu.config;

import com.wu.api.UserService;
import com.wu.service.RpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chenghao on 9/8/16.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
public class ServiceConfiguration {
    @Bean
    public RpcClient rpcClient(){
        return new RpcClient().addClass(UserService.class);
    }
}
