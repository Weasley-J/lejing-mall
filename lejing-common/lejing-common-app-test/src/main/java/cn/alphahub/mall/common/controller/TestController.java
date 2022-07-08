package cn.alphahub.mall.common.controller;

import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.common.entity.WwwResponse;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/name")
    public Result<Map<String, Object>> getName() {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("姓名 name", "张三");
        log.info("{}", JSONUtil.toJsonStr(map));
        return Result.ok(map);
    }

    @GetMapping("/obj")
    public Result<Object> getObj() {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("姓名 name", "张三");
        log.info("姓名 {}", JSONUtil.toJsonStr(map));
        return Result.ok(map);
    }

    @GetMapping("/obj/exception")
    public Result<Object> getObjWithException() {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("name", "张三");
        log.info("姓名 {}", JSONUtil.toJsonStr(map));
        int i = 1 / 0;
        return Result.ok(map);
    }

    @GetMapping("/response/no/escape")
    public Map<String, Object> getResponseNoEscape() {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("name", "张三");
        log.info("姓名 {}", JSONUtil.toJsonStr(map));
        return map;
    }

    @GetMapping("/response/with/escape")
    public WwwResponse getResponseWithEscape() {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("name", "张三");
        WwwResponse response = new WwwResponse();
        response.setData(map);
        log.info("姓名 {}", JSONUtil.toJsonStr(response));
        return response;
    }


    @GetMapping("/response/with/str")
    public String getResponseWithString() {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("name", "张三");
        log.info("姓名 {}", JSONUtil.toJsonStr(map));
        return cn.alphahub.common.util.JSONUtil.toJsonStr(map);
    }

}
