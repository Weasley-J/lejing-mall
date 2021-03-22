package cn.alphahub.mall.thirdparty.sms.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.util.DateUtils;
import cn.alphahub.mall.thirdparty.sms.service.SmsService;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
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
public class SmsController {

    @Resource
    private SmsService smsService;

    /**
     * 查询短信发送详情
     *
     * @param telephone 手机号
     * @return 发送结果
     */
    @GetMapping("/query")
    public BaseResult<QuerySendDetailsResponse> querySendDetails(
            @RequestParam(name = "telephone") String telephone,
            @RequestParam("sendDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sendDate
    ) {
        // 组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        // 必填-号码
        request.setPhoneNumber(telephone);
        // 必填-短信发送的日期 支持30天内记录查询（可查其中一天的发送数据），格式yyyyMMdd
        request.setSendDate(DateUtils.parseDateToStr("yyyyMMdd", sendDate));
        // 必填-页大小
        request.setPageSize(10L);
        // 必填-当前页码从1开始计数
        request.setCurrentPage(1L);
        try {
            QuerySendDetailsResponse response = smsService.querySendDetails(request);
            return BaseResult.ok(response);
        } catch (ClientException e) {
            log.error("发送短信异常：{}\n", e.getMessage(), e);
            return BaseResult.fail();
        }
    }
}
