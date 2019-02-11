package com.cloud.zuul.controller;


import com.cloud.common.ResultBody;
import com.cloud.zuul.service.ZuulForwardLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HandleLoggerController {

    @Autowired
    ZuulForwardLoggerService zuulForwardLoggerService;

    @GetMapping(value = "/gateway/logger")
    public ResultBody find() {
        return zuulForwardLoggerService.findAll();
    }
}
