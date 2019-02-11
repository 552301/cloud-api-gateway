package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableZuulProxy
@EnableAuthorizationServer
@EnableFeignClients(
        basePackages = {"com.cloud.zuul.feign"}
)

@EnableJpaRepositories(
        basePackages = {"com.cloud.zuul.dao.mysql","com.cloud.security.dao"}
)
@EnableCassandraRepositories(
        basePackages = {"com.cloud.zuul.dao.cassandra"}
)
public class BootstrapApplication {

    public static void main(String[] args) {

        SpringApplication.run(BootstrapApplication.class, args);

    }

}

