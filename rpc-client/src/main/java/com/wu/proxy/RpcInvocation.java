package com.wu.proxy;

import com.wu.RequestBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcInvocation<T> implements InvocationHandler {

    private Class<T> interfaceClass;

    public RpcInvocation(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Class<?>[] parameterType = method.getParameterTypes();

        RequestBean requestBean = new RequestBean();
        requestBean.setClassName(className);
        requestBean.setMethodName(methodName);
        requestBean.setParameters(args);
        requestBean.setParametersType(parameterType);
        return null;
    }
}
