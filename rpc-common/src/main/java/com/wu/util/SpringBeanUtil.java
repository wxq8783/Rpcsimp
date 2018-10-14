package com.wu.util;

import org.springframework.context.ConfigurableApplicationContext;

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

    public void setContext(ConfigurableApplicationContext context)
    {
        this.context = context;
    }

    public <T> T getBean(Class<T> type)
    {
        return (T)INSTANCE.getBean(type);
    }

    public void registerBean(String beanName, Object object)
    {
        this.context.getBeanFactory().registerSingleton(beanName, object);
    }
}
