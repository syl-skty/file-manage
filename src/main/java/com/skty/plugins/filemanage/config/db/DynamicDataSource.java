package com.skty.plugins.filemanage.config.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源，根据当前的配置动态选择对应的数据源进行数据处理
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 将与当前线程绑定的数据源名进行返回，后面使用的时候将会使用这个key去数据源map中获取当前的数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getCurrentDataSourceKey();
    }
}
