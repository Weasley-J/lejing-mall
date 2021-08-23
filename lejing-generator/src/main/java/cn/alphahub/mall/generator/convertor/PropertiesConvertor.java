package cn.alphahub.mall.generator.convertor;


import com.alibaba.druid.pool.DruidDataSource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * properties convertor
 *
 * @author lwj
 * @version 1.0
 * @date 2021/08/23
 */
@Mapper(componentModel = "default")
public interface PropertiesConvertor {

    PropertiesConvertor INSTANCE = Mappers.getMapper(PropertiesConvertor.class);

    /**
     * DataSourceProperties -> DruidDataSource
     *
     * @param properties data source properties
     * @return druid data source
     */
    DruidDataSource copy(DataSourceProperties properties);
}
