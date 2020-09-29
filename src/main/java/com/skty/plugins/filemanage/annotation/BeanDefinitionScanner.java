package com.skty.plugins.filemanage.annotation;

import org.springframework.beans.factory.config.BeanDefinition;

import java.util.Set;

/**
 * @author zhaoyun
 * @date 2020/9/10 18:08
 */
public interface BeanDefinitionScanner {

    /**
     * 执行扫描，返回得到的所有bean定义
     */
    Set<BeanDefinition> doScan();

}
