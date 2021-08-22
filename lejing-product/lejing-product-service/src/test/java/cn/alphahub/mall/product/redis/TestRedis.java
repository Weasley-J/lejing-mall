package cn.alphahub.mall.product.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <b>RedisTemplate测试</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/11
 */
@Slf4j
@SpringBootTest
public class TestRedis {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @BeforeEach
    void setUp() {
        System.out.println("-------------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("------------------------");
    }

    @Test
    @Order(1)
    void testSetString() throws JsonProcessingException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 23);
        stringRedisTemplate.opsForValue().set("person", new ObjectMapper().writeValueAsString(map), TimeUnit.SECONDS.toSeconds(10));
    }

    @Test
    @Order(2)
    void testGetString() {
        String string = stringRedisTemplate.opsForValue().get("person");
        System.out.println("person = " + string);
    }
}
