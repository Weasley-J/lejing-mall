package cn.alphahub.mall.generator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库类型
 *
 * @author lwj
 */
@Getter
@AllArgsConstructor
public enum DbType {

    /**
     * mysql - mysql数据库
     */
    MYSQL("mysql", "mysql数据库"),
    ORACLE("oracle", "oracle数据库"),
    SQLSERVER("sqlserver", "sqlserver数据库"),
    POSTGRESQL("postgresql", "postgresql数据库"),
    MONGODB("mongodb", "mongodb数据库"),
    ;

    private final String code;
    private final String name;
}
