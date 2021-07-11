package cn.alphahub.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * jackson序列化工具类
 *
 * @author liuwenjing
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String ERROR_MSG = "json序列化出错";

    @Nullable
    public static String toJsonStr(Object data) {
        if (data == null) {
            return null;
        }
        if (data.getClass() == String.class) {
            return (String) data;
        }
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error(ERROR_MSG + ":{}", data, e);
            return null;
        }
    }

    @Nullable
    public static <T> T parseString(String json, Class<T> tClass) {
        try {
            return MAPPER.readValue(json, tClass);
        } catch (IOException e) {
            log.error(ERROR_MSG + ":{}", json, e);
            return null;
        }
    }

    @Nullable
    public static <E> List<E> parseList(String json, Class<E> eClass) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            log.error(ERROR_MSG + ":{}", json, e);
            return null;
        }
    }

    @Nullable
    public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            log.error(ERROR_MSG + ":{}", json, e);
            return null;
        }
    }

    @Nullable
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            log.error(ERROR_MSG + ":{}", json, e);
            return null;
        }
    }

    public static void printJsonStr(Object data) {
        System.out.println(toJsonStr(data));
    }
}
