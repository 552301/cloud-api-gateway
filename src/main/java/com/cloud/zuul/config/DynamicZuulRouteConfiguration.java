package com.cloud.zuul.config;


import com.cloud.zuul.service.impl.DynamicZuulRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicZuulRouteConfiguration {

    @Autowired
    private ZuulProperties zuulProperties;

    @Autowired
    private ServerProperties serverProperties;

    @Bean
    @ConditionalOnMissingBean(DynamicZuulRouteLocator.class)
    public DynamicZuulRouteLocator dynamicZuulRouteLocator(){
        if (null == zuulProperties) {
            zuulProperties = new ZuulProperties();
        }
        DynamicZuulRouteLocator routeLocator = new DynamicZuulRouteLocator(this.serverProperties.getServlet().getContextPath(),this.zuulProperties);
        return routeLocator;
    }

}
