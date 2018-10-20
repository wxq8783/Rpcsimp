package com.wu.service;

import com.wu.proxy.RpcInvocation;

import java.lang.reflect.Proxy;

public class RpcClientService<T> {


    public  <T> T  createProxy(Class<T> interfaceClass){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RpcInvocation<T>(interfaceClass));
    }


}
