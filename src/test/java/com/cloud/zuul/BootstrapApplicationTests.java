package com.cloud.zuul;

import com.cloud.zuul.dao.ZuulForwardLoggerDao;
import com.cloud.zuul.dao.ZuulRouteInfoDao;
import com.cloud.zuul.entity.ZuulRouteInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootstrapApplicationTests {

    @Autowired
    private ZuulRouteInfoDao zuulRouteInfoDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ZuulForwardLoggerDao zuulForwardLoggerDao;

    @Test
    public void contextLoads() {
        List<ZuulRouteInfo> list = zuulRouteInfoDao.findAll();
        System.out.println(list);
    }

    @Test
    public void encode() {
        System.out.println(passwordEncoder.encode("123456"));
    }

}

