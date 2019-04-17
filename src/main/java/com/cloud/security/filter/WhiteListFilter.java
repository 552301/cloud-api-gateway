package com.cloud.security.filter;

import com.cloud.security.util.WhiteListToken;
import com.cloud.zuul.service.ZuulWhiteListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 白名单管理
 * 如果客户单发起的请求地址是白名单列表中地址，则不需要进行后续的校验
 */
@Configuration
@Slf4j
public class WhiteListFilter extends OncePerRequestFilter {

    @Autowired
    private ZuulWhiteListService whiteListService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String path = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();

        if (whiteListService.isWhitelist(method, path)) {
            log.info("白名单资源，url 是: {}, 方法是：{}", path, method);
            WhiteListToken whiteList = new WhiteListToken("whiteList", path);
            SecurityContextHolder.getContext().setAuthentication(whiteList);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
