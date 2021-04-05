package cn.alphahub.mall.auth.controller;

import cn.alphahub.common.constant.AuthConstant;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.auth.config.WeChatProperties;
import cn.alphahub.mall.auth.feign.MemberClient;
import cn.alphahub.mall.member.domain.Member;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 微信登录 - 登录 & 授权回调
 *
 * @author liuwenjing
 * @date 2021年4月4日
 */
@Slf4j
@Controller
@RequestMapping(value = "/wx")
public class WeChatController {

    public static final String LEJING_HOME = "http://lejing.com";
    public static final String LEJING_AUTH_HOME = "http://auth.lejing.com";

    @Resource
    private MemberClient memberClient;
    @Resource
    private WeChatProperties weChatProperties;

    /**
     * 获取扫码人的信息
     * <P>添加数据</P>
     *
     * @return 扫码人的信息
     */
    @GetMapping(value = "/callback")
    public String callback(String code, String state, HttpSession session) throws Exception {

        try {
            //得到授权临时票据code
            log.info("code: {}, state: {}", code, state);

            //从redis中将state获取出来，和当前传入的state作比较
            //如果一致则放行，如果不一致则抛出异常：非法访问

            // 向认证服务器发送请求换取access_token
            //2、拿着code请求 微信固定的地址，得到两个 access_token 和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            //拼接三个参数：id 秘钥 和 code 值
            String accessTokenUrl = String.format(baseAccessTokenUrl, weChatProperties.getAppId(), weChatProperties.getAppSecret(), code);

            String accessTokenInfo = HttpUtil.get(accessTokenUrl);

            log.info("使用微信的accessToken登录注册用户: \n{}", JSONUtil.toJsonPrettyStr(accessTokenInfo));

            BaseResult<Member> result = memberClient.loginWithWeChat(accessTokenInfo);
            if (result.getSuccess()) {
                Member member = result.getData();
                log.info("登录成功：用户信息：\n{}", JSONUtil.toJsonPrettyStr(member));
                //1、第一次使用session，命令浏览器保存卡号，JSESSIONID这个cookie
                // 以后浏览器访问哪个网站就会带上这个网站的cookie
                //TODO 1、默认发的令牌。当前域（解决子域session共享问题）
                //TODO 2、使用JSON的序列化方式来序列化对象到Redis中
                session.setAttribute(AuthConstant.LOGIN_USER, member);
                //2、登录成功跳回首页
                return "redirect:" + LEJING_HOME;
            } else {
                return "redirect:" + LEJING_AUTH_HOME + "/login.html";
            }
        } catch (Exception e) {
            log.warn("WeixinApiController异常信息：\n{}", e.getLocalizedMessage());
        }
        return "redirect:" + LEJING_AUTH_HOME + "/login.html";
    }

    /**
     * 生成微信扫描二维码图片
     *
     * @return 微信扫描二维码图片
     */
    @GetMapping(value = "/login")
    public String getWeChatQRCode() {

        // 微信开发平台授权baseUrl   %s相当于？表示占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 对redirect_url进行URLEncoder编码
        String redirectUrl = URLEncoder.encode(weChatProperties.getRedirectUrl(), StandardCharsets.UTF_8);

        // 防止csrf攻击（跨站请求伪造攻击）
        String state = "hjl.mynatapp.cc";
        // 为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        log.info("state: {}", state);

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键: "wechar-open-state-" + httpServletRequest.getSession().getId()
        //值: satte
        //过期时间: 30分钟
        //生成qrcodeUrl

        //设置%s中的值
        String url = String.format(baseUrl, weChatProperties.getAppId(), redirectUrl, "xunqi");

        //重定向到请求微信地址
        return "redirect:" + url;
    }

}
