package com.skty.plugins.filemanage.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyun
 * @date 2020/9/7 15:23
 */
@Configuration
public class TestScan implements ApplicationContextAware, InitializingBean {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private List<String> cronList;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String[] beanNamesForAnnotation = applicationContext.getBeanNamesForAnnotation(TestAnnotation.class);
        for (String name : beanNamesForAnnotation) {
            Class<?> type = applicationContext.getType(name, false);
            TestAnnotation annotation = type.getAnnotation(TestAnnotation.class);
            Method[] methods = type.getMethods();
            cronList = new ArrayList<>();
            for (Method method : methods) {
                TestMethodAnnotation annotation1 = method.getAnnotation(TestMethodAnnotation.class);
                if (annotation1 != null) {
                    String cron = annotation1.cron();
                    cronList.add(cron);
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (cronList != null) {
            cronList.forEach(cron -> threadPoolTaskScheduler.schedule(() -> {
                //写入key到容器中
                System.out.println("我正在写入key" + cron);
            }, new CronTrigger(cron)));
        }
    }
}