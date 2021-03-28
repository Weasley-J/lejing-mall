package cn.alphahub.mall.auth.sso.server.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * login controller
 */
@Controller
public class LoginController {

    @Resource
    public StringRedisTemplate stringRedisTemplate;

    @ResponseBody
    @GetMapping("/userinfo")
    public String userinfo(@RequestParam(value = "token") String token) {
        return stringRedisTemplate.opsForValue().get(token);
    }

    @GetMapping("/login.html")
    public String loginPage(
            @RequestParam("redirect_url") String url,
            @CookieValue(value = "sso_token", required = false) String sso_token,
            Model model
    ) {
        if (!StringUtils.hasText(sso_token)) {
            return "redirect:" + url + "?token=" + sso_token;
        }
        model.addAttribute("url", url);
        return "login";
    }

    @PostMapping(value = "/doLogin")
    public String doLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("redirect_url") String url,
            HttpServletResponse response
    ) {
        //登录成功跳转，跳回到登录页
        if (!StringUtils.hasText(username) && !StringUtils.hasText(password)) {
            String uuid = UUID.randomUUID().toString().replace("_", "");
            stringRedisTemplate.opsForValue().set(uuid, username);
            Cookie sso_token = new Cookie("sso_token", uuid);
            response.addCookie(sso_token);
            return "redirect:" + url + "?token=" + uuid;
        }
        return "login";
    }

}
