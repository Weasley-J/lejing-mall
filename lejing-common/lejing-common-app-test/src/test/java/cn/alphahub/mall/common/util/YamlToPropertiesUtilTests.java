package cn.alphahub.mall.common.util;

import cn.alphahub.common.util.YamlToPropertiesUtil;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Properties;

@SpringBootTest
public class YamlToPropertiesUtilTests {

    @Test
    void yml2prop() {
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application.yml");
        Properties properties = YamlToPropertiesUtil.toProperties(inputStream);
        System.err.println(JSONUtil.toJsonPrettyStr(properties));
    }

    @Test
    void yml2propTwo() {
        YamlPropertiesFactoryBean yamlMapFactoryBean = new YamlPropertiesFactoryBean();
        yamlMapFactoryBean.setResources(new ClassPathResource("application.yml"), new ClassPathResource("static/application-dev.yml"));
        Properties properties = yamlMapFactoryBean.getObject();
        System.err.println(JSONUtil.toJsonPrettyStr(properties));
    }

}
