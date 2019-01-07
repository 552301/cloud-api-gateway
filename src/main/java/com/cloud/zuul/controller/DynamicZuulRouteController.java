package com.cloud.zuul.controller;

import com.cloud.zuul.service.DynamicZuulRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DynamicZuulRouteController {

    @Autowired
    private DynamicZuulRouteService dynamicZuulRouteService;

    @RequestMapping(value = "/gateway/refresh", method = RequestMethod.GET)
    public void refresh() {
        dynamicZuulRouteService.refresh();
    }
}
