package com.cloud.zuul.vo;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ZuulRouteInfoAddParam {

    @NotBlank(message = "路由地址不能为空")
    private String path;

    private String serviceId;

    private String url;

    private boolean stripPrefix;

    private boolean retryable;

    private boolean enabled;

    private String apiName;

    private String domainId;

    private String apiDoc;
}
