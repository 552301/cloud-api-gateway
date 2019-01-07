package com.cloud.zuul.dao;


import com.cloud.zuul.entity.ZuulRouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZuulRouteInfoDao extends JpaRepository<ZuulRouteInfo, Integer> {
}
