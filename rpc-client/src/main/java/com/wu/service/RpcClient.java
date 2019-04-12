package com.wu.service;

import com.wu.proxy.RPCFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.CollectionUtils;


import java.util.ArrayList;
import java.util.List;

public class RpcClient implements BeanFactoryPostProcessor {

    private List<Class> list = new ArrayList<>();

    public RpcClient addClass(Class clazz){
        list.add(clazz);
        return this;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        BeanDefinitionRegistry registry=(BeanDefinitionRegistry)configurableListableBeanFactory;
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        for(Class clazz : list){
            GenericBeanDefinition definition=(GenericBeanDefinition) BeanDefinitionBuilder.genericBeanDefinition(clazz).getBeanDefinition();
            definition.getPropertyValues().addPropertyValue("referenceClass",clazz);
            definition.setBeanClass(RPCFactoryBean.class);
            registry.registerBeanDefinition(clazz.getName(),definition);
        }


    }
}
