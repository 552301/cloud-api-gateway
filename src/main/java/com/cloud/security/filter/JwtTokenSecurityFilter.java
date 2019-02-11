package com.cloud.security.filter;

import com.cloud.security.util.JwtAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对用户进行进行判断，
 * 1. 白名单中地址，直接放过
 * 2. Jwt token校验
 * 3. 不做处理，放入登陆校验
 * 4。Oauth2 校验
 */
@Configuration
@Slf4j
public class JwtTokenSecurityFilter extends OncePerRequestFilter {

    // 匿名授权标识符
    private final String ANONYMOUS_USER = "anonymousUser";

    @Value("${security.header}")
    private String AUTHORIZATION_HEADER_KEY = "Authorization";

    @Value("${security.salt}")
    private String SALT_KEY = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        log.info("Token 拦截器 -> 请求方式是：{}, 请求路由是：{}", httpServletRequest.getMethod(), httpServletRequest.getRequestURL().toString());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal == null || ANONYMOUS_USER.equals(principal.toString())) {
                log.info("从请求中获取到了Token信息，根据Token校验用户身份, authentication is: {}", authentication.getPrincipal());
                String token = getToken(httpServletRequest);
                JwtAuthenticationToken customToken = new JwtAuthenticationToken(AUTHORIZATION_HEADER_KEY, token);
                SecurityContextHolder.getContext().setAuthentication(customToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER_KEY);
        if (null == token || token.trim().isEmpty()) {
            log.debug("http header中Token为空，改从Cookies中获取，");
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie item : cookies) {
                    if (AUTHORIZATION_HEADER_KEY.equals(item.getName())) {
                        // 获取到token
                        return SALT_KEY + item.getValue();
                    }
                }
            }
            // 从url中获取token
            log.debug("http cookies中token为空，改从URL中获取，");
            token = request.getParameter(AUTHORIZATION_HEADER_KEY);
            if (null == token || token.trim().isEmpty()) {
                return null;
            }
        }
        return token;
    }
}
