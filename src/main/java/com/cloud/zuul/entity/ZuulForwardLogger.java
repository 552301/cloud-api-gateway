package com.cloud.zuul.entity;


import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@Data
@Table(value = "zuul_forward_logger")
public class ZuulForwardLogger {

    @PrimaryKey
    @Column(value = "id")
    private String id;

    @Column(value = "method")
    private String method;

    @Column(value = "path")
    private String path;

    @Column(value = "username")
    private String username;

    @Column(value = "query")
    private String query;

    @Column(value = "body")
    private String body;

    @Column(value = "message")
    private String message;

    @Column(value = "start_time")
    private String startTime;

    @Column(value = "end_time")
    private String endTime;

    @Column(value = "status")
    private String status;

}
