package cn.alphahub.common.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户信息
 * <p>
 * <b>用于拦截器的ThreadLocal变量中获取用户身份的数据传输对象</b>
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
     * 用户key - redis前缀
     */
    private String userKey;

    /**
     * 是否临时用户
     */
    private Boolean tempUser = Boolean.FALSE;
}
