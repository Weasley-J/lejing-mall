package cn.alphahub.mall.auth.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

/**
 * MD5密码加密解密工具类
 */
public class CodecUtils {

    /**
     * <b>使用BCryptPasswordEncoder加密密码</b>
     * <p>带盐值加密,每次返回的密文都不一样</p>
     *
     * @param rawPassword 需要加密的明文密码
     * @return 返回加盐加密后密码的密文
     * @date 2021年3月23日
     * @see org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
     */
    public static String encodePassword(CharSequence rawPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * <b>使用BCryptPasswordEncoder解密密码</b>
     * <p>判断密文是否和明文密码匹配</p>
     *
     * @param rawPassword     原明文密码
     * @param encodedPassword 原明文密码加密后的密文
     * @return 返回true，说明密码匹配正确
     * @date 2021年3月23日
     * @see org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
     */
    public static Boolean matchesPassword(CharSequence rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * md5加密
     *
     * @param data 需要加密数据
     * @param salt 盐值
     * @return 加密后的数据
     * @date 2020年3月23日
     */
    public static String md5Hex(String data, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.md5Hex(salt + DigestUtils.md5Hex(data));
    }

    /**
     * 解密md5
     *
     * @param data 加密的数据
     * @param salt 盐值
     * @return 解密后的数据
     * @date 2020年3月23日
     */
    public static String shaHex(String data, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.sha512Hex(salt + DigestUtils.sha512Hex(data));
    }

    /**
     * 生成盐
     *
     * @return 盐值
     * @date 2020年3月23日
     */
    public static String generateSalt() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }
}
