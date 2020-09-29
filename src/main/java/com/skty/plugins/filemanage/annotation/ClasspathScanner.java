package com.skty.plugins.filemanage.annotation;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Function;

/**
 *
 * 类扫描器，允许用户将指定的类路径下面的所有类进行扫描，之后扫描后将会调用指定的方法去处理
 * @author zhaoyun
 * @date 2020/9/10 10:45
 */
public  class ClasspathScanner  implements BeanDefinitionScanner{

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private final String[] scanPaths;

    private final Function<Class<?>, Object> produceFunction;

    private final Class<? extends Annotation> annotationClass;


    private BeanDefinitionRegistry registry;

    public ClasspathScanner( String[] scanPaths, Function<Class<?>,
            Object> produceFunction, Class<? extends Annotation> annotationClass) {
        this.scanPaths = scanPaths;
        this.produceFunction = produceFunction;
        this.annotationClass = annotationClass;
    }

    @Override
    public Set<BeanDefinition> doScan() {
        innerClass innerClass=new innerClass(registry);
        ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(null, new CronTrigger(""));

        return null;
    }

    /**
     * 内部使用扫描的工具
     */
    private  class innerClass extends ClassPathBeanDefinitionScanner{

        public innerClass(BeanDefinitionRegistry registry) {
            super(registry);
        }

        /**
         * 开始进行扫描
         * @param basePackages 需要扫描的基包
         * @return 包中所有符合条件的bean定义
         */
        protected Set<BeanDefinition> startScan(String... basePackages){
            Assert.notEmpty(basePackages, "At least one base package must be specified");
            Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
            for (String basePackage : basePackages) {
                Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
                for (BeanDefinition candidate : candidates) {
                    if (candidate instanceof AnnotatedBeanDefinition) {
                        AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
                    }
                    beanDefinitions.add(candidate);
                }
            }
            return beanDefinitions;
        }

        /**
         * 是否是候选的组件，用来过滤无用的数据
         * @param beanDefinition bean定义
         * @return  true/false是否允许获取
         */
        @Override
        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            Class<?> rawClass = beanDefinition.getResolvableType().getRawClass();
            if(rawClass!=null){
                return  rawClass.isAnnotationPresent(annotationClass);
            }
            return false;
        }
    }

}
