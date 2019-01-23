package com.cloud.zuul.vo;

import lombok.Data;


@Data
public class ZuulRouteInfoVo {

    private String id;

    private String path;

    private String serviceId;

    private String url;

    private boolean stripPrefix = false;

    private boolean retryable;

    private boolean enabled;

    private String apiName;

    private String domainId;

    private String description;
}
