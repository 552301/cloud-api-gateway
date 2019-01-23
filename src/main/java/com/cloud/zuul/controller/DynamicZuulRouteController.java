package com.cloud.zuul.controller;

import com.cloud.common.ResultBody;
import com.cloud.zuul.service.DynamicZuulRouteService;
import com.cloud.zuul.vo.ZuulRouteInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DynamicZuulRouteController {

    @Autowired
    private DynamicZuulRouteService dynamicZuulRouteService;

    @RequestMapping(value = "/gateway/refresh", method = RequestMethod.GET)
    public ResultBody refresh() {
        dynamicZuulRouteService.refresh();
        return ResultBody.success("Gateway refresh successfully.");
    }

    @GetMapping(value = "/gateway/routes")
    public ResultBody findAll(){
        List<ZuulRouteInfoVo> result = dynamicZuulRouteService.findAll();
        return ResultBody.success(result);
    }
}
