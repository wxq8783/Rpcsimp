package com.wu.proxy;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class RPCFactoryBean<T> implements FactoryBean {

    private Class<T> referenceClass;

    public void setReferenceClass(Class<T> referenceClass) {
        this.referenceClass = referenceClass;
    }

    /**
     * 创建bean
     * @return
     * @throws Exception
     */
    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(referenceClass.getClassLoader(),new Class[]{referenceClass},new RpcInvocation<T>(referenceClass));
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
