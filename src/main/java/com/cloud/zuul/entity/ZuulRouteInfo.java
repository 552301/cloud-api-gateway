package com.cloud.zuul.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "zuul_route_info")
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

    @Column(name = "api_name")
    private String apiName;

    @Column(name = "domain_id")
    private String domainId;

    @Column(name = "description")
    private String description;
}
