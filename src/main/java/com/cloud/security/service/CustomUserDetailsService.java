package com.cloud.security.service;

import com.cloud.security.dao.SysUserSecurityDao;
import com.cloud.security.entity.SysUserSecurity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserSecurityDao sysUserSecurityDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUserSecurity element = sysUserSecurityDao.findByUsername(s);
        if (element == null) {
            log.info("用户不存在，{}", s);
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new User(element.getUserId().toString(), element.getPassword(), new LinkedList<>());
    }
}
