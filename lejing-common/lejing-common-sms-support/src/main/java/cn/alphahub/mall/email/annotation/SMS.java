package cn.alphahub.mall.email.annotation;

import cn.alphahub.mall.email.SmsSupport;
import cn.alphahub.mall.email.enums.SmsSupplier;

/**
 * 多模板短信注解
 *
 * @author lwj
 * @version 1.0
 * @apiNote 基于此注解解析不同的短信模板, 使用注解@SMS指定以：短信供应商、短信模板发送短信
 * @date 2021-09-24
 */
public @interface SMS {

    /**
     * 默认模板名称
     */
    String DEFAULT_TEMPLATE = "DEFAULT";

    /**
     * 短信模板名称，默认：DEFAULT
     *
     * @return 短信模板名称
     */
    String name() default DEFAULT_TEMPLATE;

    /**
     * 短信供应商，默认短信供应商: 阿里云
     *
     * @apiNote 如果需要拓展其他短信供应商，见枚举{@code cn.alphahub.mall.email.enums.SmsSupplier}
     * @see cn.alphahub.mall.email.enums.SmsSupplier
     */
    SmsSupplier supplier() default SmsSupplier.ALI;

    /**
     * 自定义实现发送发送短信的实现类，必须显现或继承{@code cn.alphahub.mall.email.SmsSupport}接口
     *
     * @return 发送短信的实现类class
     * @apiNote 当指定自定义短信发送类时将优先采用自定义短信发送实现完成发送短信的逻辑
     */
    Class<? extends SmsSupport> invokeClass();
}
