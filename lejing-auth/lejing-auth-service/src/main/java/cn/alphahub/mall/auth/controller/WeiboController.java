package cn.alphahub.mall.auth.controller;

import cn.alphahub.common.constant.AuthConstant;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.util.JSONUtil;
import cn.alphahub.mall.auth.domain.SocialUser;
import cn.alphahub.mall.auth.feign.MemberClient;
import cn.alphahub.mall.member.domain.Member;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Oauth2社交登录Controller</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/28
 */
@Slf4j
@Controller
public class WeiboController {

    public static final String LEJING_HOMEPAGE = "http://lejing.com";
    public static final String LEJING_AUTH_HOMEPAGE = "http://auth.lejing.com";

    @Resource
    private MemberClient memberClient;

    /**
     * 新浪微博登录
     *
     * @param code 授权码
     * @return 跳回首页
     */
    @GetMapping(value = "/oauth2.0/weibo/success")
    public String weiboLogin(@RequestParam("code") String code, HttpSession session) {
        log.info("code:{}", code);
        Map<String, Object> map = new HashMap<>();
        map.put("client_id", "561922463");
        map.put("client_secret", "c99db94064f9d30c111a9ecc589f4cac");
        map.put("grant_type", "authorization_code");
        map.put("redirect_uri", "http://auth.lejing.com/oauth2.0/weibo/success");
        map.put("code", code);
        //1 根据用户授权返回的code换取access_token
        String url = "https://api.weibo.com/oauth2/access_token";
        String response = HttpUtil.post(url, map);
        log.info("response:{}", response);
        //2 处理
        if (StringUtils.hasText(response)) {
            //获取到了access_token,转为通用社交登录对象
            SocialUser socialUser = JSONUtil.parseString(response, SocialUser.class);

            //2.1 当前用户如果是第一次进网站，自动注册进来（为当前社交用户生成一个会员信息，以后这个社交账号就对应指定的会员）
            // 登录或者注册这个社交用户
            if (socialUser != null) {
                log.info("{}", socialUser.getAccess_token());
            }
            // 调用远程服务
            BaseResult<Member> result = memberClient.oauthLogin(socialUser);
            if (result.getSuccess()) {
                Member member = result.getData();
                log.info("登录成功, 用户信息：{}", member);
                //1、第一次使用session，命令浏览器保存卡号，JSESSIONID这个cookie, 以后浏览器访问哪个网站就会带上这个网站的cookie
                //TODO 1 默认发的令牌。当前域（解决子域session共享问题）
                //TODO 2 使用JSON的序列化方式来序列化对象到Redis中
                session.setAttribute(AuthConstant.LOGIN_USER, member);

                //2、登录成功跳回首页
                return "redirect:" + LEJING_HOMEPAGE;
            }
        }
        return "redirect:" + LEJING_AUTH_HOMEPAGE + "/login.html";
    }
}
