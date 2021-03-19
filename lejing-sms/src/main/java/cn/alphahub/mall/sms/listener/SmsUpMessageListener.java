package cn.alphahub.mall.sms.listener;

import com.aliyun.mns.model.Message;
import org.springframework.stereotype.Component;

/**
 * 如果发送的短信需要接收对方回复的状态消息，只需实现该接口并初始化一个 Spring Bean 即可
 *
 * @author Weasley J
 * @date 2021年3月19日
 */
@Component
public class SmsUpMessageListener implements com.alibaba.cloud.spring.boot.sms.SmsUpMessageListener {

    @Override
    public boolean dealMessage(Message message) {
        System.out.println(this.getClass().getName() + "; " + message.toString());
        return true;
    }
}
