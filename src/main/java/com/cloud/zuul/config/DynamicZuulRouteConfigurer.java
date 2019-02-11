package com.cloud.zuul.config;


import com.cloud.zuul.service.impl.DynamicZuulRouteLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DynamicZuulRouteConfigurer {

    @Autowired
    private ZuulProperties zuulProperties;

    @Autowired
    private ServerProperties serverProperties;

    @Bean
    @ConditionalOnMissingBean(DynamicZuulRouteLocator.class)
    public DynamicZuulRouteLocator dynamicZuulRouteLocator() {
        if (null == zuulProperties) {
            zuulProperties = new ZuulProperties();
        }
        DynamicZuulRouteLocator routeLocator = new DynamicZuulRouteLocator(this.serverProperties.getServlet().getContextPath(), this.zuulProperties);
        log.info("初始化动态路由配置");
        return routeLocator;
    }

}
