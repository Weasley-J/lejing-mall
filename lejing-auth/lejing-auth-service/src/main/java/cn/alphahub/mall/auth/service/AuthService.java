package cn.alphahub.mall.auth.service;

import cn.alphahub.common.enumeration.CheckCodeStatus;

/**
 * 授权服务顶层接口
 *
 * @author liuwenjing
 * @date 2021年3月23日
 */
public interface AuthService {

    /**
     * <b>发送验证码</b>
     * <p>使用RabbitMQ发送短信，使用Redis做短信防刷</p>
     *
     * @param phone  需要接收验证码的手机号
     * @param origin 请求验证码的来源
     * @return 验证码发送结果枚举提示
     */
    CheckCodeStatus sendCheckCode(String phone, Integer origin);
}
