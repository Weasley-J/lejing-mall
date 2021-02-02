package cn.alphahub.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 短信参数实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsParam {
    /**
     * 验证码(短信内容)
     */
    private String code;

    /**
     * 接收短信的手机号码(可以是多个)
     */
    private String[] phoneNumbers;
}
