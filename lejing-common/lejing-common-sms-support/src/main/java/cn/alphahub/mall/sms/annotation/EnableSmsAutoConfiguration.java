package cn.alphahub.mall.sms.annotation;

import cn.alphahub.mall.sms.config.SmsConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SmsConfig.class)
public @interface EnableSmsAutoConfiguration {

}
