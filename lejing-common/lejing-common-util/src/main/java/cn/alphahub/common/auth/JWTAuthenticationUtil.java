package cn.alphahub.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * JWT Authentication Util
 */
public final class JWTAuthenticationUtil {
    private static final String PRIVATE_KEY = "5d099ffdb2f823ff08e27ed671117c4ca087b0b10ab2feb961195c2b37cb0094";

    /**
     * 获取token
     *
     * @param userId   用户唯一id
     * @param userName 用户名
     * @return jwt token
     */
    public static String getToken(String userId, String userName) {
        Date currentDate = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(userId)
                .setSubject(userName)
                .setIssuedAt(currentDate)
                .setExpiration(DateUtils.addDays(currentDate, 30))
                .signWith(SignatureAlgorithm.HS256, PRIVATE_KEY);
        return builder.compact();
    }

    /**
     * 校验token
     *
     * @param token token
     * @return Claims
     */
    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(PRIVATE_KEY).parseClaimsJws(token).getBody();
    }

}
