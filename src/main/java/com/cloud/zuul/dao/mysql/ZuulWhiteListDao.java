package com.cloud.zuul.dao.mysql;

import com.cloud.zuul.entity.ZuulWhiteList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface ZuulWhiteListDao extends JpaRepository<ZuulWhiteList, Integer> {

    @Modifying
    @Transactional
    @Query(value = "update sys_zuul_white_list set delete_status = 1 where id = ?1 and delete_status = 0", nativeQuery = true)
    int logicDelete(Integer id);


    Optional<ZuulWhiteList> findById(Integer id);

    @Query(value = "select count(*) from sys_zuul_white_list where delete_status = 0 and enabled = 1 and method = ?1 and ?2 like replace(path,'**','%')", nativeQuery = true)
    int findRoute(String method, String path);
}
