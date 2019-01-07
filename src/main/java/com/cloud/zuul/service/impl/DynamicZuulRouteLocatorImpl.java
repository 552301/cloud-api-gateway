package com.cloud.zuul.service.impl;

import com.cloud.zuul.service.DynamicZuulRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


@Service
public class DynamicZuulRouteLocatorImpl implements DynamicZuulRouteService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private RouteLocator routeLocator;

    @Override
    public void refresh() {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        applicationEventPublisher.publishEvent(routesRefreshedEvent);
    }
}
