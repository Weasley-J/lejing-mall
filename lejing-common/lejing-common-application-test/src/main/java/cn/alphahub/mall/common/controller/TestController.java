package cn.alphahub.mall.common.controller;

import cn.alphahub.common.core.domain.Result;
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
    public Result<Map<String, Object>> getName() {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("name", "张三");
        return Result.ok(map);
    }

    @GetMapping("/obj")
    public Result<Object> getObj() {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("name", "张三");
        int i = 1 / 0;
        return Result.ok(map);
    }
}
