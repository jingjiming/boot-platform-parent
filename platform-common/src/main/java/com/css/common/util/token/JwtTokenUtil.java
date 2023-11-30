package com.css.common.util.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.css.common.util.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {

    public static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    // token密钥
    private static String TOKEN_SECURITY = "XXHGLYjS3DB";

    // 发布者
    public static final String ISSUER = "bowl_token";

    /**
     * 生成token
     *
     * @param claims
     * @return
     */
    public static String createToken(Map<String, String> claims) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECURITY);
            JWTCreator.Builder builder = JWT.create()
                    //.withIssuer(ISSUER)
                    //设置过期时间为2小时
                    .withExpiresAt(DateUtils.addHours(new Date(), 2));
            claims.forEach(builder::withClaim);
            return builder.sign(algorithm);
        } catch (Exception e) {
            logger.error("生成token失败,失败原因:{}", e);
        }
        return null;
    }

    /**
     * 验证jwt，并返回数据
     */
    public static Map<String, String> verifyToken(String token) {
        Algorithm algorithm;
        Map<String, Claim> map = new HashMap<>();
        try {
            algorithm = Algorithm.HMAC256(TOKEN_SECURITY);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            map = jwt.getClaims();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> resultMap = new HashMap<>(map.size());
        map.forEach((k, v) -> resultMap.put(k, v.asString()));
        return resultMap;
    }

    /**
     * 生成登录token
     *
     * @param loginInfoEntity
     * @return
     */
    public static String createLoginToken(LoginInfoEntity loginInfoEntity) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECURITY);
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer(ISSUER)
                    //设置过期时间为1个月
                    .withExpiresAt(DateUtils.addMonths(new Date(), 2));
            if (loginInfoEntity != null) {
                builder.withClaim(LoginInfoConstants.USER_ID, loginInfoEntity.getUserId());
                builder.withClaim(LoginInfoConstants.USER_NAME, loginInfoEntity.getUserName());
                builder.withClaim(LoginInfoConstants.IS_LOGIN, true);
            }
            return builder.sign(algorithm);
        } catch (Exception e) {
            logger.error("登陆生成token失败,失败原因:{}", e);
        }
        return null;
    }

    /**
     * 验证jwt，并返回数据
     */
    public static LoginInfoEntity verifyLoginToken(String token) {
        Algorithm algorithm;
        LoginInfoEntity loginInfoEntity = new LoginInfoEntity();
        try {
            algorithm = Algorithm.HMAC256(TOKEN_SECURITY);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> map = jwt.getClaims();
            loginInfoEntity.setUserId(map.get(LoginInfoConstants.USER_ID).asString());
            if (map.get(LoginInfoConstants.USER_NAME) != null) {
                loginInfoEntity.setUserName(map.get(LoginInfoConstants.USER_NAME).asString());
            }
            loginInfoEntity.setLogin(map.get(LoginInfoConstants.IS_LOGIN).asBoolean());
        } catch (Exception e) {
            loginInfoEntity = null;
            logger.error("解密token失败:{}", e);
        }
        return loginInfoEntity;
    }

    /**
     * 验证token是否过期
     *
     * @param token
     * @return
     */
    public static Boolean expireAt(String token) {
        Algorithm algorithm;
        try {
            algorithm = Algorithm.HMAC256(TOKEN_SECURITY);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            verifier.verify(token);
        } catch (TokenExpiredException e) {
            logger.error("token过期=====>:{}", token);
            return true;
        }
        return false;
    }

    /**
     * 从请求头中获取token进行校验
     *
     * @param request
     * @return
     */
    public static LoginInfoEntity getLoginInfoEntity(HttpServletRequest request) {
        String token = request.getHeader(LoginInfoConstants.TOKEN_NAME);
        //默认设置为未登录
        if (request == null || StringUtils.isBlank(token)) {
            return null;
        }
        return JwtTokenUtil.verifyLoginToken(token);
    }

    /**
     * 从请求头中获取token进行校验
     *
     * @return
     */
    public static LoginInfoEntity getLoginInfoEntity() {
        Map<String, String> reqParam = JwtTokenUtil.getHeadersInfo();
        String authorization = reqParam.get(LoginInfoConstants.TOKEN_NAME);
        //默认设置为未登录
        if (StringUtils.isBlank(authorization)) {
            return null;
        }
        return JwtTokenUtil.verifyLoginToken(authorization);
    }

    private static Map<String, String> getHeadersInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> info = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            info.put(key, value);
        }
        return info;
    }

    public static void main(String[] args) {
        LoginInfoEntity loginInfoEntity = new LoginInfoEntity();
        loginInfoEntity.setUserId("1637E67329164ED1A5AEC3D574A1B2A0");
        loginInfoEntity.setUserName("景");
        System.out.println(createLoginToken(loginInfoEntity));
    }

}
