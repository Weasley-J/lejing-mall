package cn.alphahub.mall.generator.config;

import cn.alphahub.mall.generator.enums.DbType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "code.generator")
public class GeneratorDatabaseProperties implements Serializable {
    /**
     * 数据库类型
     *
     * @see DbType
     */
    private DbType dbType;
}
