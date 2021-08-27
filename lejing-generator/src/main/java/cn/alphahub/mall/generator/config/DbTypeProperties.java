package cn.alphahub.mall.generator.config;

import cn.alphahub.mall.generator.enums.DbType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * GeneratorDatabase
 *
 * @author lwj
 * @version 1.0
 * @date 2021/08/22
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "code.generator")
public class DbTypeProperties implements Serializable {
    /**
     * 数据库类型
     *
     * @see DbType
     */
    private DbType dbType = DbType.MYSQL;
}
