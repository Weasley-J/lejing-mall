package cn.alphahub.mall.auth.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <b>微博社交用户实体</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"access_token", "expires_in", "remind_in"})
public class SocialUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，
     * 第三方应用应该用该票据和自己应用内的用户建立唯一影射关系，来识别登录状态，不能使用本返回值里的UID字段来做登录识别
     */
    private String access_token;

    /**
     * access_token的生命周期，单位是秒数
     */
    private Long expires_in;

    /**
     * access_token的生命周期（该参数即将废弃，开发者请使用expires_in）
     */
    private String remind_in;

    /**
     * 授权用户的UID，本字段只是为了方便开发者，减少一次user/show接口调用而返回的，
     * 第三方应用不能用此字段作为用户登录状态的识别，只有access_token才是用户授权的唯一票据
     */
    private String uid;

    /**
     * 是真名吗
     */
    private String isRealName;
}
