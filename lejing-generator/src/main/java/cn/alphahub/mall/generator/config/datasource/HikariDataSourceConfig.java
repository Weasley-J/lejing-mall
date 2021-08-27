package cn.alphahub.mall.generator.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * hikari data source config
 * <p>用户代码生成动态切换数据库，提示：{@code HikariDataSource}、{@code DruidDataSource}选其中一个即可
 *
 * @author lwj
 * @version 1.0
 * @date 2021/08/24
 */
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class})
public class HikariDataSourceConfig {
    /**
     * 当nacos中修改数据库配置动态切换数据库
     *
     * @return HikariDataSource
     */
    @Primary
    @RefreshScope
    @Bean(name = {"hikariDataSource"})
    public HikariDataSource hikariDataSource(DataSourceProperties properties) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        return dataSource;
    }
}
