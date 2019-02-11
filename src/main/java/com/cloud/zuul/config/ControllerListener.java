package com.cloud.zuul.config;

import com.cloud.common.HeaderConstants;
import com.cloud.zuul.dao.cassandra.ZuulForwardLoggerDao;
import com.cloud.zuul.entity.ZuulForwardLogger;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

@Aspect
@Component
public class ControllerListener {

    @Autowired
    private ZuulForwardLoggerDao zuulForwardLoggerDao;

    @Pointcut("execution(public * com.cloud.zuul.controller..*(..))")
    public void pointcut() {
    }

    @Before(value = "pointcut()")
    public void before(JoinPoint jp) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        request.setAttribute(HeaderConstants.X_REQUEST_START, System.currentTimeMillis());
    }

    @After(value = "pointcut()")
    public void afterAop(JoinPoint jp) {

        ZuulForwardLogger element = new ZuulForwardLogger();
        element.setId(UUID.randomUUID().toString().replaceAll("-", ""));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            element.setUsername(authentication.getName());
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        element.setMethod(request.getMethod());
        element.setPath(request.getRequestURI());
        Map<String, String[]> param = request.getParameterMap();
        Gson gson = new Gson();
        element.setQuery(gson.toJson(param));

        Object startTime = request.getAttribute(HeaderConstants.X_REQUEST_START);
        if (startTime != null) {
            element.setStartTime(String.valueOf(startTime));
        }

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        element.setStatus(String.valueOf(response.getStatus()));

        element.setEndTime(String.valueOf(System.currentTimeMillis()));
        zuulForwardLoggerDao.save(element);
    }
}
