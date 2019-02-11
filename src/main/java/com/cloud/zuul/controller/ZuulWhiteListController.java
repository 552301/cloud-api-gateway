package com.cloud.zuul.controller;


import com.cloud.common.ResultBody;
import com.cloud.zuul.entity.ZuulWhiteList;
import com.cloud.zuul.service.ZuulWhiteListService;
import com.cloud.zuul.vo.ZuulWhiteListAddParamVo;
import com.cloud.zuul.vo.ZuulWhiteListUpdateParamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class ZuulWhiteListController {

    @Autowired
    private ZuulWhiteListService zuulWhiteListService;

    @PostMapping(value = "/gateway/white/list")
    public ResultBody add(@Valid @RequestBody ZuulWhiteListAddParamVo paramVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResultBody.success(3000004, errorMessage, paramVo);
        }

        ZuulWhiteList element = new ZuulWhiteList();
        BeanUtils.copyProperties(paramVo, element);
        return zuulWhiteListService.add(element);
    }

    @PutMapping(value = "/gateway/white/list")
    public ResultBody update(@Valid @RequestBody ZuulWhiteListUpdateParamVo paramVo,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResultBody.success(3000002, errorMessage, paramVo);
        }

        ZuulWhiteList element = new ZuulWhiteList();
        BeanUtils.copyProperties(paramVo, element);
        return zuulWhiteListService.update(element);
    }

    @DeleteMapping(value = "/gateway/white/list/{id}")
    public ResultBody delete(@PathVariable(value = "id") Integer id) {
        log.info("白名单id是：{}", id);

        return zuulWhiteListService.delete(id);
    }

    @GetMapping(value = "/gateway/white/list")
    public ResultBody find() {
        return zuulWhiteListService.findALl();
    }
}
