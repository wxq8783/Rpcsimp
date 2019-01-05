package com.wu.service;


import com.wu.annotation.RPCService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import com.wu.util.SpringBeanUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RpcServer implements DisposableBean, ApplicationContextAware ,ApplicationListener<WebServerInitializedEvent> {
    public void destroy() throws Exception {}

    private static int port ;

    @Autowired
    NettyServerService nettyServerService;

    @Autowired
    RpcRegisterService rpcRegisterService;



    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.INSTANCE.setContext((ConfigurableApplicationContext) applicationContext);
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
         port = event.getWebServer().getPort();
        Map<String, Object> map = SpringBeanUtil.INSTANCE.getContext().getBeansWithAnnotation(RPCService.class);
        if (!CollectionUtils.isEmpty(map)) {
            rpcRegisterService.doRegister(map , getHost(),port+10000);
            nettyStart(port+10000);
        }
    }

    public String getHost(){
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }

    //netty启动
    public void nettyStart(int port){
        nettyServerService.start(port);
    }


}
