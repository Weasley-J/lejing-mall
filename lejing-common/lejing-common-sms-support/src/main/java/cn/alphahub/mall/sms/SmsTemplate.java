package cn.alphahub.mall.sms;

import cn.alphahub.mall.sms.annotation.EnableSmsSupport;
import cn.alphahub.mall.sms.aspect.SmsAspect;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 短信模板方法
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-24
 */
@Slf4j
@Component
@Validated
@ConditionalOnBean(annotation = {EnableSmsSupport.class})
public class SmsTemplate {
    /**
     * 多模板短信配置切面
     */
    private final SmsAspect smsAspect;
    /**
     * 默认线程池
     */
    private final ThreadPoolExecutor executor;

    public SmsTemplate(SmsAspect smsAspect, ThreadPoolExecutor executor) {
        this.smsAspect = smsAspect;
        this.executor = executor;
    }

    /**
     * 发送短信
     *
     * @param smsParam 短信参数
     * @return 短信供应商的发送短信后的返回结果
     */
    public Object send(@Valid SmsParam smsParam) {
        RequestAttributes mainThreadRequestAttributes = RequestContextHolder.getRequestAttributes();
        CompletableFuture<Object> sendResponseFuture = CompletableFuture.supplyAsync(() -> {
            RequestContextHolder.setRequestAttributes(mainThreadRequestAttributes);
            SmsClient smsClient = smsAspect.getSmsClient();
            return smsClient.send(smsParam.getContent(), smsParam.getPhones());
        }, executor);
        return sendResponseFuture.getNow(null);
    }

    /**
     * 短信参数
     */
    @Data
    public static class SmsParam implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 短信内容、模板参数。多个以","隔开，若无模板参数，则为短信内容。模板参数的个数需要与【短信模板】对应模板的变量个数保持一致。
         */
        @NotBlank(message = "短信内容不能为空")
        private String content;
        /**
         * 手机号里列表
         */
        @NotEmpty(message = "手机号不能为空")
        private String[] phones;
    }
}
