package com.cloud.zuul.controller;


import com.cloud.common.RestCodeEnum;
import com.cloud.common.ResultBody;
import com.cloud.zuul.feign.system.ApiAuthRoutesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
public class AuthRoutesController {

    @Autowired
    private ApiAuthRoutesService apiAuthRoutesService;

    @ResponseBody
    @GetMapping(value = "/gateway/auth/routes")
    public ResultBody getAuthRoutes(){
        Authentication authorization = SecurityContextHolder.getContext().getAuthentication();
        if (authorization == null) {
            log.info("没有授权的用户，请重新登陆");
            return ResultBody.success(RestCodeEnum.NO_LOGIN,"登陆信息无效，请重新登陆");
        }
        log.info("请求用户是：{}", authorization.getPrincipal());

        try {
            apiAuthRoutesService.getApiAuthRoutes(authorization.getPrincipal().toString());
        } catch (Exception e) {
            log.warn("查询用户路由角色失败，用户暂时没有被收于仍和权限");
        }
        return ResultBody.success("Bingo");
    }
}
