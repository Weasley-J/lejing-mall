package cn.alphahub.mall.cart.to;

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
 * @date 2021/04/03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoTo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户密钥
     */
    private String userKey;

    /**
     * 是否临时用户
     */
    private Boolean tempUser = false;
}
