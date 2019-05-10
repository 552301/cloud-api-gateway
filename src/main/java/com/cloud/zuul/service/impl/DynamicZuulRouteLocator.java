package com.cloud.zuul.service.impl;

import com.cloud.zuul.dao.ZuulRouteInfoDao;
import com.cloud.zuul.entity.ZuulRouteInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DynamicZuulRouteLocator extends AbstractDynamicRouteLocator {

    @Autowired
    private ZuulRouteInfoDao zuulRouteInfoDao;

    public DynamicZuulRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
    }


    @Override
    public Map<String, ZuulProperties.ZuulRoute> getDynamicRoute() {
        HashMap<String, ZuulProperties.ZuulRoute> routes = new HashMap<>();
        List<ZuulRouteInfo> routeList = zuulRouteInfoDao.findAll();
        for (ZuulRouteInfo result : routeList) {
            if (StringUtils.isEmpty(result.getPath())) {
                continue;
            }
            if (StringUtils.isEmpty(result.getServiceId()) && StringUtils.isEmpty(result.getUrl())) {
                continue;
            }
            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                BeanUtils.copyProperties(result, zuulRoute);
            } catch (Exception e) {
                e.printStackTrace();
            }
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }

}
