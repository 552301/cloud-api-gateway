package com.cloud.zuul.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ZuulRouteInfoUpdateParam extends ZuulRouteInfoAddParam {
    @NotBlank(message = "路由唯一ID")
    private Integer id;
}
