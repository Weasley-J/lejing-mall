package cn.alphahub.mall.email.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    ALI("阿里云"),
    /**
     * 华为云
     */
    HUAWEI("华为云"),
    /**
     * 京东云
     */
    JINGDONG("京东云"),
    /**
     * 七牛云
     */
    QINIU("七牛云"),
    ;

    /**
     * 供应商名称
     */
    private final String name;
}
