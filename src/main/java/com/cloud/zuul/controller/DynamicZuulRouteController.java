package com.cloud.zuul.controller;

import com.cloud.common.ResultBody;
import com.cloud.zuul.service.DynamicZuulRouteService;
import com.cloud.zuul.vo.ZuulRouteInfoAddParam;
import com.cloud.zuul.vo.ZuulRouteInfoUpdateParam;
import com.cloud.zuul.vo.ZuulRouteInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class DynamicZuulRouteController {

    @Autowired
    private DynamicZuulRouteService dynamicZuulRouteService;

    @RequestMapping(value = "/gateway/refresh", method = RequestMethod.GET)
    public ResultBody refresh() {
        dynamicZuulRouteService.refresh();
        return ResultBody.success("Gateway refresh successfully.");
    }

    @GetMapping(value = "/gateway/routes")
    public ResultBody findAll(){
        List<ZuulRouteInfoVo> result = dynamicZuulRouteService.findAll();
        return ResultBody.success(result);
    }

    @PostMapping(value = "/gateway/routes")
    public ResultBody add(@RequestBody ZuulRouteInfoAddParam param){
        log.info("新增路由配置，参数是：{}", param);
        return dynamicZuulRouteService.add(param);
    }

    @DeleteMapping(value = "/gateway/routes/{id}")
    public ResultBody delete(@PathVariable(value = "id") Integer id) {
        log.info("删除路由配置信息，路由ID是：{}", id);
        return dynamicZuulRouteService.delete(id);
    }

    @PutMapping(value = "/gateway/routes")
    public ResultBody update(@RequestBody ZuulRouteInfoUpdateParam param) {
        log.info("更新路由配置信息，{}", param);
        return dynamicZuulRouteService.update(param);
    }
}
