package com.cloud.common;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 初始化日志存储表
 * */
@Configuration
@Slf4j
public class CaddandraLoggerHistoryInit {

    @Value("${cassandra.host}")
    private String host;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @PostConstruct
    public void init(){
        createKeyspace();
        createTable();
    }

    private void createKeyspace(){

        Cluster cluster = Cluster.builder().addContactPoints(host).build();

        Session session = cluster.connect();


            String query = "CREATE KEYSPACE "+ keyspace +" WITH replication "
                    + "= {'class':'SimpleStrategy', 'replication_factor':1}; ";

            try {
                session.execute(query);
            } catch (Exception e){
                log.info("{} 已经创建", keyspace);
            }

            session.close();


    }

    private void createTable(){

        Cluster cluster = Cluster.builder().addContactPoints(host).build();

        Session session = cluster.connect(keyspace);



            String query = "CREATE TABLE zuul_forward_logger(id text PRIMARY KEY, "
                    + "method text, "
                    + "path text, "
                    + "username text, "
                    + "query text," +
                    "body text," +
                    "message text," +
                    "start_time text," +
                    "end_time text," +
                    "status text, );";
            try {
                session.execute(query);
            } catch (Exception e) {
                log.info("zuul_forward_logger 已创建");
            }


            session.close();
    }
}
