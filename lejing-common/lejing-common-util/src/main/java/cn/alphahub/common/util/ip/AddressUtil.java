package cn.alphahub.common.util.ip;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 获取地址类
 *
 * @author ruoyi
 */
public class AddressUtil {
    /**
     * IP地址查询
     */
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";
    /**
     * 未知地址
     */
    public static final String UNKNOWN = "XX XX";
    private static final Logger log = LoggerFactory.getLogger(AddressUtil.class);

    private AddressUtil() {
    }

    /**
     * 更具ip地址获取地理位置信息
     *
     * @param ip ip地址
     * @return 位置信息
     */
    public static String getRealAddressByIp(String ip) {
        String address = "";
        // 内网不查询
        if (IpUtil.internalIp(ip)) {
            return "内网IP";
        }
        try {
            Map<String, Object> formMap = new LinkedHashMap<>(2);
            formMap.put("ip", ip);
            formMap.put("json", true);
            HttpResponse response = HttpUtil.createGet(IP_URL).form(formMap).execute();
            String body = response.body();
            if (StringUtils.isEmpty(body)) {
                log.error("获取地理位置异常 {}", ip);
                return UNKNOWN;
            }
            JSONObject jsonObject = JSONUtil.parseObj(body);
            String region = jsonObject.get("pro", String.class);
            String city = jsonObject.get("city", String.class);
            address = String.format("%s %s", region, city);
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }

        return StringUtils.isBlank(address) ? UNKNOWN : address;
    }
}
