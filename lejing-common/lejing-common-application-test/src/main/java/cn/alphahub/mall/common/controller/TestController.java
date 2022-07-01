package cn.alphahub.mall.common.controller;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 输入类描述
 *
 * @author weasley
 * @version 1.0
 * @date 2022/7/1
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/name")
    public Map<String, Object> getName() {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("name", "张三");
        return map;
    }
}
