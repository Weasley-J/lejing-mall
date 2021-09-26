package cn.alphahub.mall.email.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 短信供应商类型枚举枚举
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-24
 */
@Getter
@AllArgsConstructor
public enum SmsSupplier {
    /**
     * 阿里云
     */
    ALI("ALI_CLOUD", "阿里云"),
    /**
     * 华为云
     */
    HUAWEI("HUAWEI_CLOUD", "华为云"),
    /**
     * 京东云
     */
    JINGDONG("JINGDONG_CLOUD", "京东云"),
    /**
     * 七牛云
     */
    QINIU("QINIU_CLOUD", "七牛云"),
    ;

    /**
     * 供应商名称编码
     */
    private final String code;
    /**
     * 供应商名称
     */
    private final String name;

    /**
     * 获取短信供应商枚举
     *
     * @param code 供应商名称编码
     * @return 短信供应商枚举
     */
    public static SmsSupplier getEnum(String code) {
        return Stream.of(SmsSupplier.values())
                .filter(smsSupplier -> smsSupplier.getCode().equals(code))
                .findFirst().orElse(null);
    }
}
