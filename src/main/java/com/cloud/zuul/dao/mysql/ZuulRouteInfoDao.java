package com.cloud.zuul.dao.mysql;


import com.cloud.zuul.entity.ZuulRouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ZuulRouteInfoDao extends JpaRepository<ZuulRouteInfo, Integer> {

    @Modifying
    @Transactional
    @Query(value = "update sys_zuul_route_info set delete_status = 1 where id = ?1 and delete_status = 0", nativeQuery = true)
    int logicDelete(Integer id);
}
