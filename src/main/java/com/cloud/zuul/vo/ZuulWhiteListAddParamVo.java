package com.cloud.zuul.vo;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ZuulWhiteListAddParamVo {

    @NotBlank(message = "路由地址不能为空")
    private String path;

    @NotBlank(message = "请求方法不能为空")
    private String method;

    private boolean enabled;

    private String domainId;

}
