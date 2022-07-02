package cn.alphahub.mall.common.util;

import cn.alphahub.common.util.YamlToPropertiesUtil;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.Properties;

@SpringBootTest
public class YamlToPropertiesUtilTests {

    @Test
    void yml2prop() {
        InputStream inputStream = Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application.yml");
        Properties properties = YamlToPropertiesUtil.toProperties(inputStream);
        System.err.println(JSONUtil.toJsonPrettyStr(properties));
    }

}
