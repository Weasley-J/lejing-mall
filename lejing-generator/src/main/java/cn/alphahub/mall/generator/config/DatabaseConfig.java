package cn.alphahub.mall.generator.config;

import cn.alphahub.mall.generator.config.condition.MongoNonNullCondition;
import cn.alphahub.mall.generator.config.condition.MongoNullCondition;
import cn.alphahub.mall.generator.dao.GeneratorDao;
import cn.alphahub.mall.generator.dao.MongoDBGeneratorDao;
import cn.alphahub.mall.generator.dao.MySQLGeneratorDao;
import cn.alphahub.mall.generator.dao.OracleGeneratorDao;
import cn.alphahub.mall.generator.dao.PostgreSQLGeneratorDao;
import cn.alphahub.mall.generator.dao.SQLServerGeneratorDao;
import cn.alphahub.mall.generator.enums.DbType;
import cn.alphahub.mall.generator.utils.BizException;
import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 数据库配置
 *
 * @author Mark sunlightcs@gmail.com, lwj
 */
@Data
@Configuration
@EnableConfigurationProperties({DbTypeProperties.class})
public class DatabaseConfig {
    /**
     * mongo
     */
    private static boolean mongo = false;

    @Resource
    private DbTypeProperties dbTypeProperties;

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
    @RefreshScope
    @Conditional(MongoNullCondition.class)
    public GeneratorDao initGeneratorDao() {
        DbType dbType = dbTypeProperties.getDbType();
        switch (dbType) {
            case MYSQL:
                return mysqlgeneratordao;
            case ORACLE:
                return oracleGeneratorDao;
            case SQLSERVER:
                return sqlServerGeneratorDao;
            case POSTGRESQL:
                return postgresqlGeneratorDao;
            default:
                throw new BizException("不支持当前数据库：" + dbType.getName());
        }
    }

    @Bean
    @RefreshScope
    @Conditional(MongoNonNullCondition.class)
    public GeneratorDao getMongoDBDao(MongoDBGeneratorDao mongoDBGeneratorDao) {
        mongo = true;
        return mongoDBGeneratorDao;
    }
}
