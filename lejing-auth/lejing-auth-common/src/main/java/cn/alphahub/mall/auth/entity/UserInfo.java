package cn.alphahub.mall.auth.entity;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 载荷对象
 *
 * @author lwj
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long id;

    /**
     * 用户名
     */
    private String username;
}
