package cn.alphahub.mall.auth.util;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Md5Test {

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

    @BeforeEach
    void setUp() {
        System.out.println("-----------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("-----------");
    }

    /**
     * 加密
     */
    @Test
    void encodePwd() {
        String encodePassword = CodecUtils.encodePassword("123456");
        // $2a$10$dCGCD0yafSzX7uC8hJKKg.V46wp6XI07kadSwVUEyukHXBftR2Ez6
        // $2a$10$/.udUOV9lAqT9Kv96/yOHOQUxZMAQbbJg8Qs9H.iZ/njjVX3sS/we
        // $2a$10$HmGTYr8BkNKPIgv82jPxyOEMNR.9szagAaM4XU1/kgUCSj7by7lWy
        System.out.println("encode = " + encodePassword);
    }

    /**
     * 解密
     */
    @Test
    void matchesPwd() {
        Boolean matchesPassword = CodecUtils.matchesPassword("123456", "$2a$10$HmGTYr8BkNKPIgv82jPxyOEMNR.9szagAaM4XU1/kgUCSj7by7lWy");
        System.out.println("matches = " + matchesPassword);
    }
}
