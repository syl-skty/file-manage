package com.skty.plugins.filemanage.config.db;

/**
 * 动态数据源与当前线程绑定器，这边保存当前所有数据源的上下文
 */
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>() {
        /**
         * 当当前的线程没有绑定任意一个数据源时，就使用默认的数据源进行返回
         */
        @Override
        protected String initialValue() {
            return DataSourceConfig.fileManageDataSourceKey;
        }
    };

    /**
     * 获取当前线程正在使用的的dataSource名字
     *
     * @return 对应的dataSourceKey
     */
    public static String getCurrentDataSourceKey() {
        return contextHolder.get();
    }

    /**
     * 为当前线程使用的数据源key进行设置为指定的值
     */
    public static void setCurrentDataSourceKey(String dataSourceKey) {
        contextHolder.set(dataSourceKey);
    }

}
