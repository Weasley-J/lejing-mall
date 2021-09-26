package cn.alphahub.mall.email;

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
     * @param content 短信内容
     * @param phones  接收短信的手机号，可变参，可以是多个
     * @return 短信供应商的发送短信后的返回结果
     */
    Object send(String content, String... phones);
}
