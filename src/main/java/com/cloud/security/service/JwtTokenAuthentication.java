package com.cloud.security.service;


import com.cloud.security.util.JwtAuthenticationToken;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;


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

        String header = authentication.getPrincipal().toString();

        String token = authentication.getCredentials().toString();

        if (identify(token)) {
            log.info("Token解析成功");
            return new JwtAuthenticationToken(header,token,null);
        } else {
            log.info("Token解析错误");
            throw new BadCredentialsException("Token已经失效，请重新登陆");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(JwtAuthenticationToken.class);
    }


    private boolean identify(String token) {
        if (token != null) {
            try {
                Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.substring(AUTHORIZATION_SALT_KEY.length()));
                return true;
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
                log.warn(e.getMessage());
                return false;
            }
        }
        return false;
    }
}
