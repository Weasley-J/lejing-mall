package cn.alphahub.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;


public final class YamlToPropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(YamlToPropertiesUtil.class);

    private YamlToPropertiesUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Converts Yaml to Properties
     *
     * @param in yaml文件的输入流
     * @return Properties
     */
    public static Properties toProperties(InputStream in) {
        Yaml yaml = new Yaml();
        TreeMap<String, Map<String, Object>> config = yaml.loadAs(in, TreeMap.class);
        logger.info("{}", String.format("%s%n\nConverts to Properties:%n%n%s", config.toString(), toPropertiesString(config)));
        return toProperties(config);
    }

    private static String toPropertiesString(TreeMap<String, Map<String, Object>> config) {
        StringBuilder sb = new StringBuilder();
        for (String key : config.keySet()) {
            sb.append(toString(key, config.get(key)));
        }
        return sb.toString();
    }

    private static Properties toProperties(TreeMap<String, Map<String, Object>> config) {
        Properties properties = new Properties();
        String formatted = toPropertiesString(config);
        String separator = System.getProperty("line.separator");
        String[] splits = formatted.split(separator);
        for (String str : splits) {
            String[] split = str.split("=");
            String key = split[0];
            String value = str.substring(key.length() + 1);
            properties.put(key, value);
        }
        return properties;
    }

    private static String toString(String key, Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String mapKey = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                sb.append(toString(String.format("%s.%s", key, mapKey), (Map<String, Object>) value));
            } else {
                sb.append(String.format("%s.%s=%s%n", key, mapKey, value.toString()));
            }
        }
        return sb.toString();
    }
}
