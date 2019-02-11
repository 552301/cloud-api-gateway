package com.cloud.zuul.entity;


import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_zuul_route_info")
@Where(clause = "delete_status = 0")
public class ZuulRouteInfo {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "path")
    private String path;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "url")
    private String url;

    @Column(name = "stripPrefix")
    private boolean stripPrefix = false;

    @Column(name = "retryable")
    private boolean retryable;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "domain_id")
    private String domainId;

    @Column(name = "api_name")
    private String apiName;

    @Column(name = "api_doc")
    private String apiDoc;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private String createTime;
}
