package com.wu.service;

import com.wu.annotation.RCPReference;
import com.wu.proxy.RPCFactoryBean;
import com.wu.registrybean.BeanUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
public class RpcClientService implements DisposableBean, ApplicationContextAware ,BeanPostProcessor {

    private Set<Field> referenceFieldSet = new HashSet<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        Field[] fields = bean.getClass().getDeclaredFields();
        if(fields != null){
            for(Field field : fields){
                RCPReference reference = field.getAnnotation(RCPReference.class);
                if(reference != null){
                    referenceFieldSet.add(field);
                }
            }
        }
        return bean;
    }

    @Override
    public void destroy() throws Exception {

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("-------------------");
        if(!CollectionUtils.isEmpty(referenceFieldSet)){
            Iterator<Field> iterator = referenceFieldSet.iterator();
            while(iterator.hasNext()){
                Field field = iterator.next();
                String serviceName = field.getName();
                if(BeanUtil.registryBeanMap.get(serviceName) != null){
                    continue;
                }
                Class<?> classType = field.getType();
                //FactoryBean<?> factoryBean = new RPCFactoryBean<>(classType);
                registryBeanFactory(applicationContext,classType);
            }
        }
    }

    public  void registryBeanFactory(ApplicationContext applicationContext , Class<?> clazz){
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        GenericBeanDefinition definition=(GenericBeanDefinition) BeanDefinitionBuilder.genericBeanDefinition(clazz).getBeanDefinition();
        definition.getPropertyValues().addPropertyValue("referenceClass",clazz);
        definition.setBeanClass(RPCFactoryBean.class);
        ((BeanDefinitionRegistry) configurableApplicationContext.getBeanFactory()).registerBeanDefinition(clazz.getName(),definition);
    }
}
