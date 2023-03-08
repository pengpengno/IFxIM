package com.ifx.common.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.security.SecurityConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * jwt 工具类
 * @author pengpeng
 * @description
 * @date 2023/3/2
 */
public class AccountJwtUtil {

    private static final Cache<String, String> JWT_CACHE = Caffeine.newBuilder()
            .expireAfterWrite(SecurityConstants.JWT_LACK_TIME,SecurityConstants.JWT_LACK_TIME_UNIT)
            .maximumSize(SecurityConstants.MAX_JWT)
            .build();


    /**
     * @param subject jwt 信息主题
     * @param claim 信息内容
     * @return
     */
    public static String generateJwt(String subject,AccountInfo claim){
        return JWT_CACHE.get(subject,(str)-> defaultJwtBuilder()
                .claim(SecurityConstants.ACCOUNT_JWT_SIGN,claim)
                .compact());
    }


    public static AccountInfo verifyAndGetClaim(String jwt ){
        return verifyJwt(jwt).get(SecurityConstants.ACCOUNT_JWT_SIGN,AccountInfo.class);
    }



    protected static JwtBuilder defaultJwtBuilder(){
        byte[]  jwtSecret = Base64.getUrlEncoder().encode(SecurityConstants.JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        // Generate the JWT token
        DateTime date = DateUtil.date();
        return Jwts.builder()
                .setIssuedAt(date)
                .setExpiration(DateUtil.offsetDay(date,1))
                .signWith(Keys.hmacShaKeyFor(jwtSecret)) // Set the signature algorithm and secret key
                ;
    }


    /***
     * 验证jwt
     * @param jwtToken
     *
     */
    protected static Claims verifyJwt(String jwtToken) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException{
        // Verify the JWT token
        return  parseJwt(jwtToken).getBody();
    }


    /***
     * 验证jwt
     * @param jwtToken
     *
     */
    protected static Jws<Claims> parseJwt(String jwtToken) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException{
        // Verify the JWT token
        return Jwts.parserBuilder()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                // Set the secret key
                .build()
                .parseClaimsJws(jwtToken) // Parse the JWT token
                ;
    }
}
