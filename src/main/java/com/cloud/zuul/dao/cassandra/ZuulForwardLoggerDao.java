package com.cloud.zuul.dao.cassandra;


import com.cloud.zuul.entity.ZuulForwardLogger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZuulForwardLoggerDao extends CrudRepository<ZuulForwardLogger, Integer> {
}
