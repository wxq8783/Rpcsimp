package com.wu.proxy;

import com.google.common.reflect.AbstractInvocationHandler;
import com.wu.RequestBean;
import com.wu.netty.NettyChannelManager;
import com.wu.netty.NettyClient;
import io.netty.channel.Channel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

public class RpcInvocation<T> extends AbstractInvocationHandler {

    private Class<T> interfaceClass;

    public RpcInvocation(Class<T> interfaceClass ) {
        this.interfaceClass = interfaceClass;
    }


    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] objects) throws Throwable {
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Class<?>[] parameterType = method.getParameterTypes();
        RequestBean requestBean = new RequestBean();
        requestBean.setClassName(className);
        requestBean.setMethodName(methodName);
        requestBean.setParameters(objects);
        requestBean.setParametersType(parameterType);
        NettyClient nettyClient = null;
        CompletableFuture future = nettyClient.sendRequest(requestBean);
        return future.get();
    }
}
