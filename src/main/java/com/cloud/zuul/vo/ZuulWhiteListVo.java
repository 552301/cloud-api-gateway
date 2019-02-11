package com.cloud.zuul.vo;

import lombok.Data;

@Data
public class ZuulWhiteListVo {

    private Integer id;

    private String path;

    private String method;

    private boolean enabled;

    private String domainId;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;

}
