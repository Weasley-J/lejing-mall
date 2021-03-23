package cn.alphahub.mall.auth.vo;

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
public class UserInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 登录用户名
     */
    private String loginacct;

    /**
     * 用户密码
     */
    private String password;
}
