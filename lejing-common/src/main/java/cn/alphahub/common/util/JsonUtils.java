package cn.alphahub.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class JsonUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    @Nullable
    public static String serialize(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    @Nullable
    public static <T> T parse(String json, Class<T> tClass) {
        try {
            return MAPPER.readValue(json, tClass);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <E> List<E> parseList(String json, Class<E> eClass) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }
}
