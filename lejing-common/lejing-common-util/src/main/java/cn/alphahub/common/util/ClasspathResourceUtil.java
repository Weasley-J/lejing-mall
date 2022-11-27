package cn.alphahub.common.util;

import cn.hutool.core.io.IoUtil;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Classpath Resource Util
 *
 * @author weasley
 * @version 1.0.0
 */
public final class ClasspathResourceUtil {

    /**
     * 获取 'classpath' 下模板内容,类路径开始不带：/
     */
    public static String readContent(String fileClasspath) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileClasspath);
        return IoUtil.read(inputStream, StandardCharsets.UTF_8);
    }
}
