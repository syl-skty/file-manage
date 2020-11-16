//package com.skty.plugins.filemanage.annotation;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.support.AbstractBeanDefinition;
//import org.springframework.beans.factory.support.BeanDefinitionBuilder;
//import org.springframework.beans.factory.wiring.BeanWiringInfo;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//
//import java.lang.annotation.Annotation;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.function.Function;
//
///**
// * @author zhaoyun
// * @date 2020/9/10 18:07
// */
//public class SpringEmbedScanner implements ApplicationContextAware, BeanDefinitionScanner {
//
//    private ApplicationContext context;
//
//    private final Function<Class<?>, Object> produceFunction;
//
//    private final Class<? extends Annotation> annotationClass;
//
//    public SpringEmbedScanner(Function<Class<?>, Object> produceFunction, Class<? extends Annotation> annotationClass) {
//        this.produceFunction = produceFunction;
//        this.annotationClass = annotationClass;
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.context = applicationContext;
//    }
//
//
//    @Override
//    public Set<BeanDefinition> doScan() {
//        String[] beanNamesForAnnotation = context.getBeanNamesForAnnotation(annotationClass);
//        Set<BeanDefinition> beanDefinitions = new HashSet<>();
//        if (beanNamesForAnnotation.length > 0) {//存在注解类
//            for (String beanName : beanNamesForAnnotation) {
//                Class<?> type = context.getType(beanName);
//                if (type != null) {
//                    AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(FactoryBeanClassBridge.class)
//                            .addConstructorArgValue(type)
//                            .setAutowireMode(BeanWiringInfo.AUTOWIRE_BY_TYPE)
//                            .getBeanDefinition();
//                    beanDefinitions.add(beanDefinition);
//                }
//            }
//        }
//        return beanDefinitions;
//    }
//
//
//    /**
//     * 代理类传输中介(会创建一个代理类指定当前类的所有执行方法)
//     */
//    private class FactoryBeanClassBridge implements FactoryBean<Object> {
//        private final Class<?> targetClass;
//
//        private FactoryBeanClassBridge(Class<?> targetClass) {
//            this.targetClass = targetClass;
//        }
//
//        @Override
//        public Object getObject() throws Exception {
//            return produceFunction.apply(targetClass);
//        }
//
//        @Override
//        public Class<?> getObjectType() {
//            return targetClass;
//        }
//
//        @Override
//        public boolean isSingleton() {
//            return true;
//        }
//    }
//
//}
