package cn.alphahub.mall.generator.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * druid动态数据源配置
 * <p>用户代码生成动态切换数据库，提示：{@code HikariDataSource}、{@code DruidDataSource}选其中一个即可
 *
 * @author lwj
 */
//@Configuration
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
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setUrl(properties.getUrl());
        dataSource.setName(properties.getName());
        return dataSource;
    }

}
