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
 * Jackson序列化工具类
 * <p>Jackson是一款十分优秀的json序列化工具，但是使用其序列化、反序列化java对象时是动不动就抛异常，于是封装这几个常用的静态方法</p>
 * <p>序列化的对象必须要实现{@code Serializable}接口</p>
 *
 * @author liuwenjing
 */
@Slf4j
public class JSONUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String ERROR_MSG = "json序列化出错";

    private JSONUtil() {
    }

    /**
     * 对象转json字符串
     *
     * @param data 要转换成的JSON的java对象
     * @return JSON字符串
     */
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

    /**
     * 解析json字符串为泛型对象
     *
     * @param json      json字符串
     * @param typeClass 泛型对象class
     * @param <T>       泛型对象
     * @return 泛型对象
     */
    @Nullable
    public static <T> T parseString(String json, Class<T> typeClass) {
        try {
            return MAPPER.readValue(json, typeClass);
        } catch (IOException e) {
            log.error(ERROR_MSG + ":{}", json, e);
            return null;
        }
    }

    /**
     * 解析json字符串为泛型对象集合
     *
     * @param json      json字符串
     * @param typeClass 泛型对象class
     * @param <T>       泛型对象
     * @return 泛型对象集合
     */
    @Nullable
    public static <T> List<T> parseList(String json, Class<T> typeClass) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(List.class, typeClass));
        } catch (IOException e) {
            log.error(ERROR_MSG + ":{}", json, e);
            return null;
        }
    }

    /**
     * 解析json字符串为Map
     *
     * @param json   json字符串
     * @param kClass class of map's key
     * @param vClass class of map's val
     * @param <K>    key的泛型
     * @param <V>    val的泛型
     * @return 泛型Map集合
     */
    @Nullable
    public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            log.error(ERROR_MSG + ":{}", json, e);
            return null;
        }
    }

    /**
     * 原生TypeReference<T>解释json字符串
     *
     * @param json json字符串
     * @param type type reference
     * @param <T>  泛型
     * @return 解析后的对象
     */
    @Nullable
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            log.error(ERROR_MSG + ":{}", json, e);
            return null;
        }
    }

    /**
     * 屏幕打印输出json字符串
     *
     * @param data 数据对象
     */
    public static void printJsonStr(Object data) {
        System.err.println(JSONUtil.toJsonStr(data));
    }
}
