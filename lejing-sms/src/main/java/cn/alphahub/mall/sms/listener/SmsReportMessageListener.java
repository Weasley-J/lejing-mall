package cn.alphahub.mall.sms.listener;

import com.aliyun.mns.model.Message;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * 如果需要监听短信是否被对方成功接收，只需实现这个接口并初始化一个 Spring Bean 即可
 *
 * @author Weasley J
 * @date 2021年3月19日
 */
@Component
public class SmsReportMessageListener implements com.alibaba.cloud.spring.boot.sms.SmsReportMessageListener {

    private List<Message> smsReportMessageSet = new LinkedList<>();

    @Override
    public boolean dealMessage(Message message) {
        smsReportMessageSet.add(message);
        System.out.println(this.getClass().getName() + "; " + message.toString());
        return true;
    }

    public List<Message> getSmsReportMessageSet() {
        return smsReportMessageSet;
    }

}
