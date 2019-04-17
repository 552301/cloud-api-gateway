package com.cloud.security.controller;


import com.cloud.common.RestCodeEnum;
import com.cloud.common.ResultBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class SecurityController {


    // 400
    @RequestMapping(value = "/error/400", produces = {"text/html"})
    public String error400Page() {
        return "/error/400";
    }

    @RequestMapping(value = "/error/400")
    @ResponseBody
    public ResultBody error400Json() {
        return ResultBody.success(RestCodeEnum.ERROR_400_JSON, "请求失败");
    }


    // 401
    @RequestMapping(value = "/error/401", produces = {"text/html"})
    public String error401Page() {
        return "/error/401";
    }

    @RequestMapping(value = "/error/401")
    @ResponseBody
    public ResultBody error401Json() {
        return ResultBody.success(RestCodeEnum.ERROR_401_JSON, "授权失败");
    }


    // 403
    @RequestMapping(value = "/error/403", produces = {"text/html"})
    public String error403Page(HttpServletRequest request) {
        Object requestUrl = request.getAttribute("javax.servlet.forward.request_uri");
        return "redirect:/login?redirect=" + requestUrl;
    }

    @RequestMapping(value = "/error/403")
    @ResponseBody
    public ResultBody error403Json() {
        return ResultBody.success(RestCodeEnum.ERROR_403_JSON, "禁止访问未授权的资源");
    }


    // 404
    @RequestMapping(value = "/error/404", produces = {"text/html"})
    public String error404Page() {
        return "/error/404";
    }

    @RequestMapping(value = "/error/404")
    @ResponseBody
    public ResultBody error404Json() {
        return ResultBody.success(RestCodeEnum.ERROR_404_JSON, "请求的资源不存在");
    }

    // 415
    @RequestMapping(value = "/error/415", produces = {"text/html"})
    public String error415Page() {
        return "/error/415";
    }

    @RequestMapping(value = "/error/415")
    @ResponseBody
    public ResultBody error415Json() {
        return ResultBody.success(RestCodeEnum.ERROR_415_JSON, "415");
    }


    // 500
    @RequestMapping(value = "/error/500", produces = {"text/html"})
    public String error500Page() {
        return "/error/500";
    }

    @RequestMapping(value = "/error/500")
    @ResponseBody
    public ResultBody error500Json() {
        return ResultBody.success(RestCodeEnum.ERROR_500_JSON, "服务内部出现错误，访问失败");
    }

}
