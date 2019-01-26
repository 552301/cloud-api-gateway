package com.cloud.security.service;


import com.cloud.security.util.JwtAuthenticationToken;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.UUID;


/**
 * 自定义身份认证
 */
@Configuration
@Slf4j
public class JwtTokenAuthentication implements AuthenticationProvider {

    @Value("${security.secret}")
    private String SECRET = "https://github.com/hzwy23";

    @Value("${security.salt}")
    private String AUTHORIZATION_SALT_KEY = "Bearer ";


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Object token = authentication.getCredentials();

        Jws<Claims> jwt = identify(token);

        if ( jwt != null ) {
            log.info("Token解析成功");
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("JwtToken");
            return new JwtAuthenticationToken(jwt.getBody().getId(), token, new ArrayList<>() {{
                add(authority);
            }});
        } else {
            log.info("Token解析错误");
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("AnonymousUser");
            return new AnonymousAuthenticationToken(UUID.randomUUID().toString(), "anonymousUser", new ArrayList<>() {{
                add(authority);
            }});
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(JwtAuthenticationToken.class);
    }


    private Jws<Claims> identify(Object token) {
        if (token != null) {
            try {
                return Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.toString().substring(AUTHORIZATION_SALT_KEY.length()));
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
                log.warn(e.getMessage());
                return null;
            }
        }
        return null;
    }
}
