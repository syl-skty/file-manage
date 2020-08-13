package com.skty.plugins.filemanage.config.db;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置
 */
@Configuration
@EnableTransactionManagement //开启事务管理
//开始mybatis的包扫描，将会自动生成mapper接口的代理类
@MapperScan(basePackages = "com.skty.plugins.filemanage.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    /**
     * 默认数据源
     */
    public static final String fileManageDataSourceKey = "fileManage-datasource";

    /**
     * 动态数据源名
     */
    public static final String dynamicDataSourceKey = "dynamicDataSource";

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.file-manage")
    public DataSourceProperties fileManageProperties() {
        return new DataSourceProperties();
    }


    @Primary
    @Bean(fileManageDataSourceKey)
    public DataSource fileManageDataSource() {
        return fileManageProperties().initializeDataSourceBuilder().build();
    }

    @Bean(dynamicDataSourceKey)
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(fileManageDataSource());

        //将所有数据源包装到一个map中，形成映射
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(fileManageDataSourceKey, fileManageDataSource());
        dynamicDataSource.setTargetDataSources(dataSourceMap);

        return dynamicDataSource;
    }


    /**
     * 配置事务管理器
     */
    @Bean("transactionManagement")
    public DataSourceTransactionManager transactionManager() {
        //事务管理器数据源使用动态数据源
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        //配置mybatis使用时的数据源，sqlSessionFactory在执行sql时，会调用动态数据源的getConnection方法，这里面会动态返回对应的数据源连接，将会自动选择对应的数据源
        sessionFactory.setDataSource(dynamicDataSource());

        //配置要扫描的mapper映射xml文件的位置
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mappers/*.xml"));
        return sessionFactory.getObject();
    }

}
