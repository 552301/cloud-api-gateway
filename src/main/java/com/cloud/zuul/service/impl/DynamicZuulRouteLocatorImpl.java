package com.cloud.zuul.service.impl;

import com.cloud.common.RestCodeEnum;
import com.cloud.common.ResultBody;
import com.cloud.zuul.dao.mysql.ZuulRouteInfoDao;
import com.cloud.zuul.entity.ZuulRouteInfo;
import com.cloud.zuul.service.DynamicZuulRouteService;
import com.cloud.zuul.vo.ZuulRouteInfoAddParam;
import com.cloud.zuul.vo.ZuulRouteInfoUpdateParam;
import com.cloud.zuul.vo.ZuulRouteInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class DynamicZuulRouteLocatorImpl implements DynamicZuulRouteService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private RouteLocator routeLocator;

    @Autowired
    private ZuulRouteInfoDao zuulRouteInfoDao;

    @Override
    public void refresh() {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        applicationEventPublisher.publishEvent(routesRefreshedEvent);
    }

    @Override
    public List<ZuulRouteInfoVo> findAll() {
        List<ZuulRouteInfo> result = zuulRouteInfoDao.findAll();
        List<ZuulRouteInfoVo> ret = new ArrayList<>();
        for (ZuulRouteInfo item : result) {
            ZuulRouteInfoVo element = new ZuulRouteInfoVo();
            BeanUtils.copyProperties(item, element);
            ret.add(element);
        }
        return ret;
    }

    @Override
    public ResultBody add(ZuulRouteInfoAddParam param) {
        ZuulRouteInfo args = new ZuulRouteInfo();
        BeanUtils.copyProperties(param, args);
        args.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        args = zuulRouteInfoDao.save(args);
        if (args.getId() == null) {
            return ResultBody.success(RestCodeEnum.ZUUL_ROUTE_ADD_ERROR, param);
        }
        return ResultBody.success();
    }

    @Override
    public ResultBody delete(Integer id) {
        int size = zuulRouteInfoDao.logicDelete(id);
        if (size > 0) {
            return ResultBody.success("删除路由配置成功");
        } else {
            return ResultBody.success(RestCodeEnum.ROUTE_ID_NOT_FOUND, id);
        }
    }

    @Override
    public ResultBody update(ZuulRouteInfoUpdateParam param) {
        ZuulRouteInfo args = new ZuulRouteInfo();
        BeanUtils.copyProperties(param, args);
        args = zuulRouteInfoDao.save(args);
        if (args.getId() == null) {
            return ResultBody.success(RestCodeEnum.ZUUL_ROUTE_UPDATE_ERROR, param);
        }
        return ResultBody.success();
    }
}
