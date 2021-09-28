package cn.alphahub.mall.sms;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 多模板、多供应商短信发送上层函数接口
 *
 * @author lwj
 * @version 1.0
 * @date 2021-09-24
 */
@FunctionalInterface
public interface SmsClient {

    /**
     * 发送短信
     *
     * @param content 短信内容、模板参数。多个以","隔开，若无模板参数，则为短信内容。模板参数的个数需要与【短信模板】对应模板的变量个数保持一致。
     * @param phones  接收短信的手机号，可变参，可以是多个
     * @return 短信供应商的发送短信后的返回结果
     */
    Object send(String content, String... phones);

    /**
     * 入参校验
     *
     * @param content 短信内容、模板参数
     * @param phones  接收短信的手机号，可变参，可以是多个
     * @return parameter is empty
     */
    default boolean parameterIsEmpty(String content, String... phones) {
        final Log log = LogFactory.getLog(SmsClient.class);
        if (StringUtils.isBlank(content)) {
            log.error("短信内容不能为空.");
            return true;
        }
        if (StringUtils.isAllBlank(phones)) {
            log.error("手机号不能为空.");
            return true;
        }
        return false;
    }
}
