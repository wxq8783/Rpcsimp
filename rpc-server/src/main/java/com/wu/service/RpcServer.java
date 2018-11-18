package com.wu.service;


import com.wu.annotation.RPCService;
import java.util.Map;

import com.wu.util.SpringBeanUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RpcServer implements InitializingBean, DisposableBean, ApplicationContextAware ,ApplicationListener<EmbeddedServletContainerInitializedEvent> {
    public void destroy() throws Exception {}

    @Autowired
    NettyServerService nettyServerService;

    @Autowired
    RpcRegisterService rpcRegisterService;

    public void afterPropertiesSet() throws Exception {

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.INSTANCE.setContext((ConfigurableApplicationContext) applicationContext);
    }

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        System.out.println("==========================onApplicationEvent");
        int port = event.getEmbeddedServletContainer().getPort();
        Map<String, Object> map = SpringBeanUtil.INSTANCE.getContext().getBeansWithAnnotation(RPCService.class);
        if (!CollectionUtils.isEmpty(map)) {
            rpcRegisterService.doRegister(map , port);
        }
        nettyServerService.start( port);

    }
}
