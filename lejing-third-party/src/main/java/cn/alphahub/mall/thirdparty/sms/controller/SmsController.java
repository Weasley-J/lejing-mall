package cn.alphahub.mall.thirdparty.sms.controller;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.mall.thirdparty.sms.service.SmsService;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 短信Controller
 *
 * @author Weasley J
 * @date 2021年3月19日
 */
@Slf4j
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Resource
    private SmsService smsService;

    /**
     * 发送验证码给用户手机
     *
     * @param phone  接收验证码的手机号
     * @param origin 请求来源: 1-使用移动端请求验证码,2-使用浏览器请求验证码,0-未知来源,默认: 0;
     *               验证码长度: 移动端4位,浏览器6位, origin为空验证码长度6位;
     * @return 操作提示
     */
    @GetMapping("/sendCode")
    public Result<Boolean> sendCheckCode(
            @RequestParam("phone") String phone,
            @RequestParam(value = "origin", defaultValue = "0") Integer origin
    ) {
        Boolean send = smsService.sendCheckCode(phone, origin);
        return Result.ok(send);
    }

    /**
     * 查询短信发送详情
     *
     * @param telephone 接收验证码的手机号
     * @param sendDate  发送日期
     * @return 发送结果
     */
    @GetMapping("/sendStatus")
    public Result<QuerySendDetailsResponse> querySendDetails(
            @RequestParam(name = "telephone") String telephone,
            @RequestParam("sendDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sendDate
    ) {
        QuerySendDetailsResponse response = smsService.querySendDetails(telephone, sendDate);
        return Result.ok(response);
    }
}
