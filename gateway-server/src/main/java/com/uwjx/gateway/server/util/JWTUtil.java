package com.uwjx.gateway.server.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/26 19:04
 */
@Slf4j
public class JWTUtil {

    public static final long TOKEN_EXPIRE_TIME = 7200 * 1000;
    private static final String ISSUER = "cheng";

    public static String generateToken(String username, String secretKey) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        String token = JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(now)
                .withExpiresAt(expireTime)
                .withClaim("username", username)
                .sign(algorithm);

        return token;
    }

    public static void main(String[] args) {
        log.warn("生成 token : {}" , generateToken("wanghuan" , "pwd"));

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjaGVuZyIsImV4cCI6MTYyNDcxMzMxMiwiaWF0IjoxNjI0NzA2MTEyLCJ1c2VybmFtZSI6IndhbmdodWFuIn0.2Q9i6b_-6E95i_rSdv2_aTCpm9ABrT3PnucdkA7GEKU";
        String token2 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjQ3NDk3NzMsInVzZXJfbmFtZSI6ImJncyIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIwZTQ3YTAxZi1iYWJkLTQ0MzctOWViOC04M2JmNDkyMmNkYjgiLCJjbGllbnRfaWQiOiJjbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.0nR2_kLS8obweSHlkErox1tT9_8nV6opz-4Ox22-FFw";
        String token3 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjQ3NTAwMTYsInVzZXJfbmFtZSI6ImJncyIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJkMDY4Mjk1Yy0xMWFhLTQ0ZmQtOTZkMy1iZWY3Yzg2YzQ2YTciLCJjbGllbnRfaWQiOiJjbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.3UnjEdcOJtjEiLwj2bw3vHTy6t57dmXQZ7BwwjqQ6TI";
        log.warn("获取用户信息:{}" , getUserInfo(token3));
        log.warn("token是否有效:{}" , verifyToken(token3 , "zjxf"));
    }


    public static boolean verifyToken(String token, String secretKey) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            jwtVerifier.verify(token);
        } catch (JWTDecodeException jwtDecodeException) {
            log.warn("token解析失败");
            return false;
        }
        return true;
    }

    public static String getUserInfo(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        String username = decodedJWT.getClaim("user_name").asString();
        Date exp = decodedJWT.getExpiresAt();
//        long expire = Long.parseLong(exp + "000");
//        log.warn("过期时间秒: {}" , expire);
//        log.warn("当前时间: {}" , System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
//            Date date = format.parse(exp+"000");
            log.warn("过期时间:{}" , format.format(exp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }
}
