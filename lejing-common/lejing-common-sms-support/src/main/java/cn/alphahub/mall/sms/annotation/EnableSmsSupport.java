package cn.alphahub.mall.sms.annotation;

import cn.alphahub.mall.sms.SmsTemplate;
import cn.alphahub.mall.sms.aspect.SmsAspect;
import cn.alphahub.mall.sms.config.SmsConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enable SMS Support
 *
 * @author lwj
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({SmsConfig.class, SmsAspect.class, SmsTemplate.class})
public @interface EnableSmsSupport {

}
