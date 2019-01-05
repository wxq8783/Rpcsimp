package com.wu.proxy;

import com.google.common.reflect.AbstractInvocationHandler;
import com.wu.RequestBean;
import com.wu.ResponseBean;
import com.wu.netty.NettyClient;
import java.lang.reflect.Method;
import java.util.UUID;
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
        requestBean.setRequestId(UUID.randomUUID().toString().replace("-",""));
        requestBean.setClassName(className);
        requestBean.setMethodName(methodName);
        requestBean.setParameters(objects);
        requestBean.setParametersType(parameterType);
        NettyClient nettyClient = new NettyClient(className);
        CompletableFuture future = nettyClient.sendRequest(requestBean);
        ResponseBean bean = (ResponseBean) future.get();
        return bean.getResult();
    }
}
