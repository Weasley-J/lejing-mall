package cn.alphahub.mall.generator.service;

import cn.alphahub.mall.generator.config.MongoManager;
import cn.alphahub.mall.generator.dao.GeneratorDao;
import cn.alphahub.mall.generator.dao.MongoDBGeneratorDao;
import cn.alphahub.mall.generator.factory.MongoDBCollectionFactory;
import cn.alphahub.mall.generator.utils.BizException;
import cn.alphahub.mall.generator.utils.GenUtils;
import cn.alphahub.mall.generator.utils.PageUtils;
import cn.alphahub.mall.generator.utils.Query;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 *
 * @author Mark sunlightcs@gmail.com, lwj
 */
@Data
@Slf4j
@Service
@RefreshScope
public class SysGeneratorService {

    @Value("${spring.cloud.nacos.server-addr:127.0.0.1:8848}")
    private String serverAddr;

    @Value("${spring.cloud.nacos.config.namespace:}")
    private String namespace;

    private String dataId = "generator.properties";

    private String group = "DEFAULT_GROUP";

    @Autowired
    @Qualifier("getGeneratorDao")
    private GeneratorDao generatorDao;

    public PageUtils queryList(Query query) {
        Page<?> page = PageHelper.startPage(query.getPage(), query.getLimit());
        List<Map<String, Object>> list = generatorDao.queryList(query);
        int total = (int) page.getTotal();
        if (generatorDao instanceof MongoDBGeneratorDao) {
            total = MongoDBCollectionFactory.getCollectionTotal(query);
        }
        return new PageUtils(list, total, query.getLimit(), query.getPage());
    }

    public Map<String, String> queryTable(String tableName) {
        return generatorDao.queryTable(tableName);
    }

    public List<Map<String, String>> queryColumns(String tableName) {
        return generatorDao.queryColumns(tableName);
    }

    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            GenUtils.generatorCode(table, columns, zip);
        }
        if (MongoManager.isMongo()) {
            GenUtils.generatorMongoCode(tableNames, zip);
        }


        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 将nacos中存储的Properties配置文件取出来
     * <p>代码生成配置信息
     *
     * @return nacos中存储的Properties配置文件
     */
    public Properties getGeneratorProperties() {
        Properties queryProperties = new Properties();
        queryProperties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        queryProperties.put(PropertyKeyConst.NAMESPACE, namespace);
        try {
            ConfigService configService = NacosFactory.createConfigService(queryProperties);
            String config = configService.getConfig(dataId, group, 3000L);
            if (StringUtils.isNotBlank(config)) {
                Properties properties = new Properties();
                properties.load(new StringReader(config));
                return properties;
            } else {
                return PropertiesLoaderUtils.loadAllProperties("generator.properties");
            }
        } catch (Exception e) {
            throw new BizException("获取配置文件失败，", e);
        }
    }
}
