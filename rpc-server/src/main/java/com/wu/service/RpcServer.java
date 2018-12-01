package com.wu.service;


import com.wu.annotation.RCPReference;
import com.wu.annotation.RPCService;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import com.wu.util.SpringBeanUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RpcServer implements BeanPostProcessor, DisposableBean, ApplicationContextAware ,ApplicationListener<WebServerInitializedEvent> {
    public void destroy() throws Exception {}

    private static int port ;

    @Autowired
    NettyServerService nettyServerService;

    @Autowired
    RpcRegisterService rpcRegisterService;

    @Autowired
    RpcClient rpcClient;


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("===========最先启动===============setApplicationContext");
        SpringBeanUtil.INSTANCE.setContext((ConfigurableApplicationContext) applicationContext);
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        System.out.println("===========第三启第===============onApplicationEvent");
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

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        if(fields != null){
            for(Field field : fields){
                RCPReference reference = field.getAnnotation(RCPReference.class);
                if(reference != null){
                    rpcClient.initNettyClient(field.getType().getName());
                }
            }
        }
        return bean;
    }
}
