package com.cloud.zuul.service;

import com.cloud.zuul.vo.ZuulRouteInfoVo;

import java.util.List;

public interface DynamicZuulRouteService {
    /**
     * 刷新网关，网关重新从数据库获取路由转发信息
     */
    void refresh();

    List<ZuulRouteInfoVo> findAll();

}
