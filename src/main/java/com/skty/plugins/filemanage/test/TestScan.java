package com.skty.plugins.filemanage.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.wiring.BeanWiringInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * @author zhaoyun
 * @date 2020/9/7 15:23
 */
@Configuration
public class TestScan implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {


    private  ApplicationContext context;



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
         String[] names = context.getBeanNamesForAnnotation(TestAnnotation.class);
         int i=0;
        for (String name : names) {
            final Class<?> type = context.getType(name);

             Method[] methods = type.getMethods();

            for (Method method : methods) {
                 TestMethodAnnotation annotation = method.getAnnotation(TestMethodAnnotation.class);
                 if(annotation!=null){
                     BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(TestFactoryBean.class);
                     GenericBeanDefinition beanDefinition =(GenericBeanDefinition) builder.getBeanDefinition();
                     ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
                     constructorArgumentValues.addIndexedArgumentValue(0,name);
                     constructorArgumentValues.addIndexedArgumentValue(1, method);
                     beanDefinition.setAutowireMode(BeanWiringInfo.AUTOWIRE_BY_NAME);
                     registry.registerBeanDefinition("test"+i++, beanDefinition);
                 }
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            //beanFactory.getBeanNamesForAnnotation();
        System.out.println("========bean工厂创建完毕=====");
    }
}