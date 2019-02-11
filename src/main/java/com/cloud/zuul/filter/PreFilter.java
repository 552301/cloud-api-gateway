package com.cloud.zuul.filter;

import com.cloud.common.HeaderConstants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@Slf4j
public class PreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return "用户未登陆";
        }

        String userId = authentication.getPrincipal().toString();

        log.info("用户ID是：{}", userId);

        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.addZuulRequestHeader(HeaderConstants.X_USER_HEADER, userId);

        requestContext.getRequest().setAttribute(HeaderConstants.X_USER_HEADER, userId);
        requestContext.getRequest().setAttribute(HeaderConstants.X_REQUEST_START, System.currentTimeMillis());
        return null;
    }
}
