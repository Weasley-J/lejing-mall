/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package cn.alphahub.mall.generator.config;

import cn.alphahub.mall.generator.dao.GeneratorDao;
import cn.alphahub.mall.generator.dao.MongoDBGeneratorDao;
import cn.alphahub.mall.generator.dao.MySQLGeneratorDao;
import cn.alphahub.mall.generator.dao.OracleGeneratorDao;
import cn.alphahub.mall.generator.dao.PostgreSQLGeneratorDao;
import cn.alphahub.mall.generator.dao.SQLServerGeneratorDao;
import cn.alphahub.mall.generator.enums.DbType;
import cn.alphahub.mall.generator.utils.BizException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;

/**
 * 数据库配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@RefreshScope
@Configuration
public class DatabaseConfig {
    private static boolean mongo = false;

    @Value("${code.generator.db-type: MYSQL}")
    private DbType dbType;

    @Resource
    private MySQLGeneratorDao mysqlgeneratordao;

    @Resource
    private OracleGeneratorDao oracleGeneratorDao;

    @Resource
    private SQLServerGeneratorDao sqlServerGeneratorDao;

    @Resource
    private PostgreSQLGeneratorDao postgresqlGeneratorDao;

    public static boolean isMongo() {
        return mongo;
    }

    @Bean
    @Primary
    @Conditional(MongoNullCondition.class)
    public GeneratorDao getGeneratorDao() {
        if (DbType.MYSQL == dbType) {
            return mysqlgeneratordao;
        } else if (DbType.ORACLE == dbType) {
            return oracleGeneratorDao;
        } else if (DbType.SQLSERVER == dbType) {
            return sqlServerGeneratorDao;
        } else if (DbType.POSTGRESQL == dbType) {
            return postgresqlGeneratorDao;
        } else {
            throw new BizException("不支持当前数据库：" + dbType);
        }
    }

    @Bean
    @Primary
    @Conditional(MongoCondition.class)
    public GeneratorDao getMongoDBDao(MongoDBGeneratorDao mongoDBGeneratorDao) {
        mongo = true;
        return mongoDBGeneratorDao;
    }
}
