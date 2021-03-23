package cn.alphahub.mall.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <b>社交用户信息</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌
     */
    private String access_token;

    /**
     * 记住我
     */
    private String remind_in;

    /**
     * 过期时间
     */
    private Long expires_in;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 是真名
     */
    private String isRealName;

}
