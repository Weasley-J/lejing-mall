package cn.alphahub.mall.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <b>用户信息</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 登录账户(用户名,手机号,邮箱)
     */
    private String loginacct;

    /**
     * 用户密码
     */
    private String password;
}
