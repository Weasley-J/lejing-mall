package cn.alphahub.mall.search;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import javax.annotation.Resource;

@SpringBootTest
class LejingSearchApplicationTests {

    @Resource
    private RestHighLevelClient client;

    @Resource
    private ElasticsearchRestTemplate restTemplate;

    @Test
    void contextLoads() {

    }

}
