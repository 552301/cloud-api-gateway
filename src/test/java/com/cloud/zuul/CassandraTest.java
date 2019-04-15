package com.cloud.zuul;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CassandraTest {


    @Test
    public void contextLoads() {

        Cluster cluster = Cluster.builder().addContactPoints("121.42.143.130").build();


        Session session = cluster.connect();

        String query = "CREATE KEYSPACE gateway WITH replication "
                + "= {'class':'SimpleStrategy', 'replication_factor':1}; ";
        session.execute(query);


        session.close();
    }

    @Test
    public void createTable() {

        Cluster cluster = Cluster.builder().addContactPoints("121.42.143.130").build();


        Session session = cluster.connect("gateway");

        String dropTable = "drop table zuul_forward_logger;";

        session.execute(dropTable);

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

        session.execute(query);

        session.close();
    }
}
