package com.cloud.zuul.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public abstract class AbstractDynamicRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private ZuulProperties properties;

    public AbstractDynamicRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
    }


    public abstract Map<String, ZuulProperties.ZuulRoute> getDynamicRoute();


    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        HashMap<String, ZuulProperties.ZuulRoute> routeLinkedHashMap = new HashMap<>();
        routeLinkedHashMap.putAll(super.locateRoutes());
        routeLinkedHashMap.putAll(getDynamicRoute());
        HashMap<String, ZuulProperties.ZuulRoute> values = new HashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routeLinkedHashMap.entrySet()) {
            String path = entry.getKey();

            if (!path.startsWith("/")) {
                path = "/" + path;
            }

            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());

        }
        return values;
    }
}
