package cn.alphahub.mall.auth.util;


import cn.alphahub.common.reflect.ReflectUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

public class PropertyToUnderline {


    @BeforeEach
    void setUp() {
        System.out.println("-----------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("-----------");
    }

    /**
     * 将实体类里指定字段由"驼峰命名"风格转为"下划线"风格
     */
    @Test
    void encodePwd() {
        System.out.println(ReflectUtil.propertyToUnderline(SocialUser::getAccessToken));
        System.out.println(ReflectUtil.propertyToUnderline(SocialUser::getExpiresIn));
        System.out.println(ReflectUtil.propertyToUnderline(SocialUser::getUid));
        System.out.println(ReflectUtil.propertyToUnderline(SocialUser::getIsRealName));
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SocialUser implements Serializable {
        private static final long serialVersionUID = 1L;
        // 访问令牌
        private String accessToken;
        // 过期时间
        private Long expiresIn;
        // 用户id
        private String uid;
        // 是真名吗
        private String isRealName;
    }
}
