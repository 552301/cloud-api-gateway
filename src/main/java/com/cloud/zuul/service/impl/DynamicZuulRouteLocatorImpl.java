package com.cloud.zuul.service.impl;

import com.cloud.zuul.dao.ZuulRouteInfoDao;
import com.cloud.zuul.entity.ZuulRouteInfo;
import com.cloud.zuul.service.DynamicZuulRouteService;
import com.cloud.zuul.vo.ZuulRouteInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DynamicZuulRouteLocatorImpl implements DynamicZuulRouteService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private RouteLocator routeLocator;

    @Autowired
    private ZuulRouteInfoDao zuulRouteInfoDao;

    @Override
    public void refresh() {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        applicationEventPublisher.publishEvent(routesRefreshedEvent);
    }

    @Override
    public List<ZuulRouteInfoVo> findAll() {
        List<ZuulRouteInfo> result = zuulRouteInfoDao.findAll();
        List<ZuulRouteInfoVo> ret = new ArrayList<>();
        for(ZuulRouteInfo item: result) {
            ZuulRouteInfoVo element = new ZuulRouteInfoVo();
            BeanUtils.copyProperties(item,element);
            ret.add(element);
        }
        return ret;
    }
}
