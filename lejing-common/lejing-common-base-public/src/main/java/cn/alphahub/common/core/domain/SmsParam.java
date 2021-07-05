package cn.alphahub.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 短信参数实体
 *
 * @author liuwenjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsParam implements Serializable {
    private static final long serialVersionUID = -726542410710010L;

    /**
     * 验证码(短信内容)
     */
    private String code;

    /**
     * 接收短信的手机号码(可以是多个)
     */
    private String[] phone;
}
