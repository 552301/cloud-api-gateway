package com.cloud.security.filter;

import com.cloud.security.util.JwtAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 经过认证后，才会进入这个过滤器
 * 如果用户没有登陆系统，不会进入这个过滤器
 */
@Configuration
@Slf4j
public class TokenFilterSecurityInterceptor extends OncePerRequestFilter {

    @Value("${security.header}")
    private String AUTHORIZATION_HEADER_KEY = "Authorization";

    @Value("${security.salt}")
    private String SALT_KEY = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        log.info("Token 拦截器 -> 请求方式是：{}, 请求路由是：{}", httpServletRequest.getMethod(), httpServletRequest.getRequestURL().toString());

        String token = getToken(httpServletRequest);
        String path = httpServletRequest.getRequestURI();

        if ("/oauth/token".equals(path)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }


        if (null != token) {
            log.info("从请求中获取到了Token信息，根据Token校验用户身份");
            JwtAuthenticationToken customToken = new JwtAuthenticationToken(AUTHORIZATION_HEADER_KEY, token);
            SecurityContextHolder.getContext().setAuthentication(customToken);

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
