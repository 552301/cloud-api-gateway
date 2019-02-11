package com.cloud.zuul.vo;

import lombok.Data;

@Data
public class ZuulForwardLoggerVo {

    private String id;

    private String method;

    private String path;

    private String username;

    private String query;

    private String body;

    private String message;

    private String startTime;

    private String endTime;

    private String status;

    private long costTime;
}
