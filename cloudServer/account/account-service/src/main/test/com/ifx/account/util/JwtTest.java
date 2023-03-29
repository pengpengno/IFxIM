package com.ifx.account.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.security.SecurityConstants;
import com.ifx.common.utils.AccountJwtUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/10
 */
@Slf4j
public class JwtTest {


    @Test
    public void jwtTest(){
        byte[]  jwtSecret = Base64.getUrlEncoder().encode(SecurityConstants.JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        // Generate the JWT token
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.ES256);

        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.ES256);
        PrivateKey aPrivate = keyPair.getPrivate();
        PublicKey aPublic = keyPair.getPublic();

        String format = aPublic.getFormat();
        log.info("公钥 为 {}",format);
//        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret);
//        String format = secretKey.getFormat();
        log.info("  ser  format {}", aPrivate.getFormat());
//        log.info("  ser  format {}",format);
        AccountInfo build = AccountInfo.builder()
                .account("pengpeng")
                .userName("鹏鹏")
                .build();
        DateTime date = DateUtil.date();
        String compact = Jwts.builder()
                .setIssuedAt(date)
                .setExpiration(DateUtil.offsetDay(date, 1))
                .setSubject("pengpeng")
                .claim("te",build)
                .signWith(aPrivate) // Set the signature algorithm and secret key
                .compact();
        log.info("出现format 为 {}", compact);

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(aPublic)
                .build()
                .parseClaimsJws(compact);

//        Object body = parse.getBody();

        AccountJwtUtil.verifyAndGetClaim(compact);

    }
}
