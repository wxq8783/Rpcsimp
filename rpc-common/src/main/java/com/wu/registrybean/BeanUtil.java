package com.wu.registrybean;

import com.wu.util.SpringBeanUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BeanUtil implements BeanDefinitionRegistryPostProcessor {

    public static Map<String,Object> registryBeanMap = new ConcurrentHashMap<>();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        BeanDefinitionRegistry registry=(BeanDefinitionRegistry)beanDefinitionRegistry;
        List<Class<?>> beanClazzList = null;
        for(Class<?> clazz : beanClazzList){
            GenericBeanDefinition definition=(GenericBeanDefinition) BeanDefinitionBuilder.genericBeanDefinition(clazz).getBeanDefinition();
            definition.getPropertyValues().addPropertyValue("innerClass",clazz);
            definition.getPropertyValues().addPropertyValue("factory",beanDefinitionRegistry);
            definition.setBeanClass(null);
            registry.registerBeanDefinition(clazz.getName(),definition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }



    public static boolean registryBean(String beanId,Class<?> clazz){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        BeanDefinition definition = builder.getBeanDefinition();
        getRegistry().registerBeanDefinition(beanId,definition);
        return true;
    }

    /**
     * 获取注册者
     * @return
     */
    public static BeanDefinitionRegistry getRegistry(){
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringBeanUtil.INSTANCE.getContext();
        return (BeanDefinitionRegistry) configurableApplicationContext.getBeanFactory();
    }

    /**
     * 为已知的class创建bean,可以设置bean的属性，可以用作动态代理对象的bean的扩展
     * @param beanId
     * @param factoryClazz
     * @param beanClazz
     * @return
     */
    public static boolean registryBeanWithEdit(String beanId , Class<?> factoryClazz , Class<?> beanClazz){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getBeanDefinition();
        definition.getPropertyValues().add("referenceClass",beanClazz);
        definition.setBeanClass(factoryClazz);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        getRegistry().registerBeanDefinition(beanId,definition);
        registryBeanMap.put(beanId,0);
        return true;
    }


    public static boolean registryBeanWithDymicEdit(String beanId , Class<?> factoryClazz ,Class<?> beanClazz , String params){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getBeanDefinition();
        definition.getPropertyValues().add("interfaceClass",beanClazz);
        definition.getPropertyValues().add("params",params);
        definition.setBeanClass(factoryClazz);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        getRegistry().registerBeanDefinition(beanId,definition);
        return true;
    }


}
