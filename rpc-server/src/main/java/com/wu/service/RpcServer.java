package com.wu.service;


import com.wu.annotation.RPCService;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

public class RpcServer
        implements InitializingBean, DisposableBean, ApplicationContextAware
{
    public void destroy() throws Exception {}

    public void afterPropertiesSet() throws Exception {
        System.out.println("----------------------afterPropertiesSet");
        throw new Error("抛出异常");
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("========================setApplicationContext");
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(RPCService.class);
        if (!CollectionUtils.isEmpty(map))
        {
            RpcRegisterService rpcRegisterService = (RpcRegisterService)applicationContext.getBean("rpcRegisterService");
            rpcRegisterService.doRegister(map);
        }
    }
}
