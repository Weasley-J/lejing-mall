package cn.alphahub.common.util;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * UR工具类
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/15
 */
@Slf4j
public class URLUtil {

    /**
     * 判断一个url网址的合法性
     *
     * @param urlString URL链接
     * @return true合法，false不合法
     */
    public static boolean isValidUrl(String urlString) {
        URI uri;
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            log.warn("URL链接不合法:{}", e.getLocalizedMessage());
            return false;
        }

        if (uri.getHost() == null) {
            return false;
        }

        return "http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme());
    }
}
