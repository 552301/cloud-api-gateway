package com.cloud.security.controller;

import com.cloud.common.ResultBody;
import com.cloud.security.service.SysUserSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class LoginController {


    @Autowired
    private SysUserSecurityService sysUserSecurityService;


    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }


    @ResponseBody
    @PostMapping(value = "/gateway/user/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultBody register(@RequestParam(value = "username") String username,
                               @RequestParam(value = "password") String password,
                               @RequestParam(value = "mobilePhone") String mobilePhone,
                               @RequestParam(value = "code") String code) {

        log.info("用户注册，账号：{}, 手机号：{}, 验证码：{}", username, mobilePhone, code);
        return sysUserSecurityService.register(username, password,mobilePhone,code);
    }
}
