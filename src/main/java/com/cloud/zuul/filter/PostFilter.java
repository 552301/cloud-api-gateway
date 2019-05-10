package com.cloud.zuul.filter;

import com.cloud.common.HeaderConstants;
import com.cloud.zuul.dao.ZuulForwardLoggerDao;
import com.cloud.zuul.entity.ZuulForwardLogger;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

@Configuration
@Slf4j
public class PostFilter extends ZuulFilter {

    @Autowired
    private ZuulForwardLoggerDao zuulForwardLoggerDao;

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 9999;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        ZuulForwardLogger element = new ZuulForwardLogger();
        element.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        RequestContext context = RequestContext.getCurrentContext();
        String method = context.getRequest().getMethod();
        String path = context.getRequest().getRequestURI();
        int status = context.getResponse().getStatus();

        Map<String, String[]> param = context.getRequest().getParameterMap();
        Gson gson = new Gson();
        element.setQuery(gson.toJson(param));

        Object userId = context.getRequest().getAttribute(HeaderConstants.X_USER_HEADER);
        Object startTime = context.getRequest().getAttribute(HeaderConstants.X_REQUEST_START);
        element.setUsername(userId.toString());
        element.setMethod(method);
        element.setPath(path);
        element.setStatus(String.valueOf(status));
        element.setStartTime(new Timestamp(Long.valueOf(String.valueOf(startTime))));
        element.setEndTime(new Timestamp(System.currentTimeMillis()));
        zuulForwardLoggerDao.save(element);
        return null;
    }
}
