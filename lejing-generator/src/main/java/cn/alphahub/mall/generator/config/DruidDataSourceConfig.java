package cn.alphahub.mall.generator.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * druid动态数据源配置
 * <p>用户代码生成动态切换数据库
 *
 * @author lwj
 */
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class})
public class DruidDataSourceConfig {

    /**
     * 当nacos中修改数据库配置动态切换数据库
     *
     * @return DruidDataSource
     */
    @Primary
    @RefreshScope
    @Bean(name = {"druidDataSource"})
    public DruidDataSource druidDataSource(DataSourceProperties properties) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUsername(properties.getUsername());
        datasource.setPassword(properties.getPassword());
        datasource.setDriverClassName(properties.getDriverClassName());
        datasource.setUrl(properties.getUrl());
        return datasource;
    }

}
