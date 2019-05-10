package com.cloud.zuul.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;


@Data
@Table(name = "zuul_forward_logger")
@Entity
public class ZuulForwardLogger {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "method")
    private String method;

    @Column(name = "path")
    private String path;

    @Column(name = "username")
    private String username;

    @Column(name = "query")
    private String query;

    @Column(name = "body")
    private String body;

    @Column(name = "message")
    private String message;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "status")
    private String status;

}
