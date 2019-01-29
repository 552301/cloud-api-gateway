package com.cloud.zuul.entity;


import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_zuul_white_list")
@Where(clause = "delete_status = 0")
public class ZuulWhiteList {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "path")
    private String path;

    @Column(name = "method")
    private String method;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "domain_id")
    private String domainId;

    @Column(name = "create_by")
    private String  createBy;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_by")
    private String  updateBy;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "delete_status")
    private Integer deleteStatus;
}
