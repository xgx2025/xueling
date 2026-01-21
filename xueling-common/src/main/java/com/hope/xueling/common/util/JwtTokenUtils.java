package com.hope.xueling.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;

/**
 * JWT令牌工具类，提供生成和验证Access Token和Refresh Token的方法
 * @author 谢光湘
 * @since 2026/1/21
 */
public class JwtTokenUtils {


    public static final String ACCESS_TOKEN_SECRET = "xueling_access_secret";
    /**
     * Access Token密钥和过期时间(30分钟)
     */
    public static final long ACCESS_TOKEN_EXPIRE = 2 * 60 * 60 * 1000;


    public static final String REFRESH_TOKEN_SECRET = "xueling_refresh_secret";
    /**
     * Refresh Token密钥和过期时间(7天)
     */
    public static final long REFRESH_TOKEN_EXPIRE = 2 * 24 * 60 * 60 * 1000;


    /**
     * 生成Access Token
     */
    public static String generateAccessToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE))
                .signWith(SignatureAlgorithm.HS256, ACCESS_TOKEN_SECRET)
                .compact();
    }

    /**
     * 生成Refresh Token
     */
    public static String generateRefreshToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE))
                .signWith(SignatureAlgorithm.HS256, REFRESH_TOKEN_SECRET)
                .compact();
    }

    /**
     * 验证Token并获取claims
     */
    public static Claims getClaimsFromToken(String token, String secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 检查Token是否过期
     */
    public static boolean isTokenExpired(String token, String secret) {
        try {
            Claims claims = getClaimsFromToken(token, secret);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            // 明确捕获过期异常
            return true;
        } catch (Exception e) {
            // 其他异常不直接视为过期，而是抛出具体异常
            throw new IllegalArgumentException("token验证失败: " + e.getMessage());
        }
    }


}