package cn.alphahub.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Cookie工具类
 *
 * @author liuwenjing
 */
public final class CookieUtils {

    static final Logger log = LoggerFactory.getLogger(CookieUtils.class);

    /**
     * 得到Cookie的值, 不编码
     *
     * @param request    request对象
     * @param cookieName cookie名称
     * @return cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, false);
    }

    /**
     * 得到Cookie的值
     *
     * @param request    request对象
     * @param cookieName cookie名称
     * @return cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        for (int i = 0; i < cookieList.length; i++) {
            if (cookieList[i].getName().equals(cookieName)) {
                if (isDecoder) {
                    try {
                        retValue = URLDecoder.decode(cookieList[i].getValue(), String.valueOf(StandardCharsets.UTF_8));
                    } catch (UnsupportedEncodingException e) {
                        log.error(e.getLocalizedMessage().concat("\n"), e);
                    }
                } else {
                    retValue = cookieList[i].getValue();
                }
                break;
            }
        }
        return retValue;
    }

    /**
     * 得到Cookie的值
     *
     * @param request    request对象
     * @param cookieName cookie名称
     * @return cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    retValue = URLDecoder.decode(cookieList[i].getValue(), encodeString);
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.error("Cookie Decode Error.", e);
        }
        return retValue;
    }

    /**
     * 生成cookie，并指定编码
     *
     * @param request      请求
     * @param response     响应
     * @param cookieName   name
     * @param cookieValue  value
     * @param encodeString 编码
     */
    public static final void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, String encodeString) {
        setCookie(request, response, cookieName, cookieValue, null, encodeString, null);
    }

    /**
     * 生成cookie，并指定生存时间
     *
     * @param request      请求
     * @param response     响应
     * @param cookieName   name
     * @param cookieValue  value
     * @param cookieMaxAge 生存时间
     */
    public static final void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, Integer cookieMaxAge) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxAge, null, null);
    }

    /**
     * 设置cookie，不指定httpOnly属性
     */
    public static final void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, Integer cookieMaxAge, String encodeString) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxAge, encodeString, null);
    }

    /**
     * 设置Cookie的值，并使其在指定时间内生效
     *
     * @param cookieMaxAge cookie生效的最大秒数
     */
    public static final void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, Integer cookieMaxAge, String encodeString, Boolean httpOnly) {
        try {
            if (StringUtils.isBlank(encodeString)) {
                encodeString = "utf-8";
            }

            if (cookieValue == null) {
                cookieValue = "";
            } else {
                cookieValue = URLEncoder.encode(cookieValue, encodeString);
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxAge != null && cookieMaxAge > 0) {
                cookie.setMaxAge(cookieMaxAge);
            }
            // 设置域名的cookie
            if (null != request) {
                cookie.setDomain(getDomainName(request));
            }
            cookie.setPath("/");

            if (httpOnly != null) {
                cookie.setHttpOnly(httpOnly);
            }
            response.addCookie(cookie);
        } catch (Exception e) {
            log.error("Cookie Encode Error.", e);
        }
    }

    /**
     * 得到cookie的域名
     *
     * @param request request对象
     * @return domain for cookie
     */
    private static final String getDomainName(HttpServletRequest request) {
        String domainName;
        String serverName = request.getRequestURL().toString();
        if (StringUtils.isEmpty(serverName) || StringUtils.isBlank(serverName)) {
            return "";
        }
        serverName = serverName.toLowerCase();
        serverName = serverName.substring(7);
        final int end = serverName.indexOf("/");
        serverName = serverName.substring(0, end);
        final String[] domains = serverName.split("\\.");
        int length = domains.length;
        // www.xxx.com.cn
        if (length > 3) {
            domainName = domains[length - 3] + "." + domains[length - 2] + "." + domains[length - 1];
        } else if (length > 1 && length <= 3) {
            // xxx.com or xxx.cn
            domainName = domains[length - 2] + "." + domains[length - 1];
        } else {
            domainName = serverName;
        }
        // www.xxx.com:8080 or www.xxx.cn:8080
        if (StringUtils.isNotBlank(domainName) || domainName.indexOf(":") > 0) {
            String[] array = domainName.split("\\:");
            domainName = array[0];
            array = domainName.split("\\.");
            domainName = array[array.length - 2] + "." + array[array.length - 1];
        }
        return domainName.startsWith(".") ? domainName.substring(1) : domainName;
    }

}
