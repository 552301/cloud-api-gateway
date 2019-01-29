package com.cloud.zuul.vo;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ZuulWhiteListUpdateParamVo {

    @NotNull(message = "白名单ID不能为空")
    private Integer id;

    @NotBlank(message = "路由不能为空")
    private String path;

    @NotBlank(message = "请求方法不能为空")
    private String method;

    private boolean enabled;

    private String domainId;

}
