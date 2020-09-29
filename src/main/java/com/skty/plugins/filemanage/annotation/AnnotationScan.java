/*
package com.skty.plugins.filemanage.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.wiring.BeanWiringInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

*/
/**
 * @author zhaoyun
 * @date 2020/9/9 19:47
 * <p>
 * 用于操作Spring容器
 * <p>
 * 依赖容器执行扫描批量获取bean定义
 * <p>
 * 获取bean定义，通过自定义扫描或者通过依赖Spring容器获取
 * <p>
 * 采用手动扫描的方式进行
 * <p>
 * 自定义扫描
 * <p>
 * 代理类传输中介(会创建一个代理类指定当前类的所有执行方法)
 * <p>
 * 代理类，只代理方法传输中介
 *//*

public class AnnotationScan implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    */
/**
 * 用于操作Spring容器
 *//*

    private ApplicationContext context;

    private Function<Class<?>, Object> produceFunction;

    private Class<? extends Annotation> annotationClass;

    private Class<?> targetClass;

    private final BeanNameGenerator beanNameGenerator = AnnotationBeanNameGenerator.INSTANCE;
    private final ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Set<BeanDefinition> beanDefinitions = getBeanDefinitions();
        if (!CollectionUtils.isEmpty(beanDefinitions)) {
            beanDefinitions.forEach(beanDefinition -> {
                ScopeMetadata scopeMetadata = scopeMetadataResolver.resolveScopeMetadata(beanDefinition);
                beanDefinition.setScope(scopeMetadata.getScopeName());
                // 生成bean的名字
                String beanName = beanNameGenerator.generateBeanName(beanDefinition, registry);
                registry.registerBeanDefinition(beanName, beanDefinition);
            });
        }
    }

    */
/**
 * 依赖容器执行扫描批量获取bean定义
 *//*

    public Set<BeanDefinition> runMode1() {
        String[] beanNamesForAnnotation = context.getBeanNamesForAnnotation(annotationClass);
        Set<BeanDefinition> beanDefinitions = new HashSet<>();
        if (beanNamesForAnnotation.length > 0) {//存在注解类
            for (String beanName : beanNamesForAnnotation) {
                Class<?> type = context.getType(beanName);
                if (type != null) {
                    AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(FactoryBeanClassBridge.class)
                            .addConstructorArgValue(type)
                            .setAutowireMode(BeanWiringInfo.AUTOWIRE_BY_TYPE)
                            .getBeanDefinition();
                    beanDefinitions.add(beanDefinition);
                }
            }
        }
        return beanDefinitions;
    }


    */
/**
 * 获取bean定义，通过自定义扫描或者通过依赖Spring容器获取
 *//*

    public Set<BeanDefinition> getBeanDefinitions() {

    }


    */
/**
 * 采用手动扫描的方式进行
 *//*

    public void runMode2(BeanDefinitionRegistry registry) {

    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }


    */
/**
 * 自定义扫描
 *//*

    private class ClassScanner extends ClassPathBeanDefinitionScanner {

        public ClassScanner(BeanDefinitionRegistry registry) {
            super(registry);
        }


    }


    */
/**
 * 代理类传输中介(会创建一个代理类指定当前类的所有执行方法)
 *//*

    private class FactoryBeanClassBridge implements FactoryBean<Object> {
        private final Class<?> targetClass;

        private FactoryBeanClassBridge(Class<?> targetClass) {
            this.targetClass = targetClass;
        }

        @Override
        public Object getObject() throws Exception {
            return produceFunction.apply(targetClass);
        }

        @Override
        public Class<?> getObjectType() {
            return targetClass;
        }

        @Override
        public boolean isSingleton() {
            return true;
        }
    }


    */
/**
 * 代理类，只代理方法传输中介
 *//*

    private static class FactoryBeanMethodBridge {

    }


}
*/
