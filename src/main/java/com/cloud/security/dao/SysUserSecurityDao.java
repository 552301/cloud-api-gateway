package com.cloud.security.dao;

import com.cloud.security.entity.SysUserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SysUserSecurityDao extends JpaRepository<SysUserSecurity, Integer> {

    @Query(value = "select user_id, username, password, enable from sys_user_security where username=?1 and del_flag = 0 limit 0,1", nativeQuery = true)
    SysUserSecurity findByUsername(@Param(value = "username") String username);
}
