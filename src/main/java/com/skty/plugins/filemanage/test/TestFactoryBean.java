package com.skty.plugins.filemanage.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhaoyun
 * @date 2020/9/7 16:56
 */
public class TestFactoryBean implements FactoryBean<TestInterface>, ApplicationContextAware {

ApplicationContext context;

//当前被代理的bean的方法名
    private final String beanName;

    //代理的bean对像类
    private final Method targetMethod;

    public TestFactoryBean(String beanName, Method targetMethod) {
        this.beanName = beanName;
        this.targetMethod = targetMethod;
    }

    @Override
    public TestInterface getObject() throws Exception {
        Object bean = context.getBean(beanName);
        return  (TestInterface)Proxy.newProxyInstance(context.getClassLoader(),new Class[]{TestInterface.class},(proxy, method, args) -> {
           return targetMethod.invoke(bean, args);
        });
    }

    @Override
    public Class<?> getObjectType() {
        return TestInterface.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
    }
}
