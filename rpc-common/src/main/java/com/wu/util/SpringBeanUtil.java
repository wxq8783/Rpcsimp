package com.wu.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

public class SpringBeanUtil {

    public static final SpringBeanUtil INSTANCE = new SpringBeanUtil();
    private ConfigurableApplicationContext context;

    private SpringBeanUtil()
    {
        if (INSTANCE != null) {
            throw new Error("SpringBeanUtil init error");
        }
    }

    public SpringBeanUtil getInstance()
    {
        return INSTANCE;
    }

    public ApplicationContext getContext(){
        return context;
    }

    public void setContext(ConfigurableApplicationContext context)
    {
        this.context = context;
    }

    public <T> T getBean(Class<T> type)
    {
        return (T)INSTANCE.getContext().getBean(type);
    }

    public <T> T getBean(String name){
        return  (T)INSTANCE.getContext().getBean(name);
    }

    public void registerBean(String beanName, Object object)
    {
        this.context.getBeanFactory().registerSingleton(beanName, object);
    }

    public Map<String, Object> getAnnotationBean(Class clazz){
        return this.context.getBeansWithAnnotation(clazz);
    }
}
