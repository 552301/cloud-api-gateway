package com.cloud.security.service.impl;

import com.cloud.common.RestCodeEnum;
import com.cloud.common.ResultBody;
import com.cloud.security.dao.SysUserSecurityDao;
import com.cloud.security.dto.SysUserDomainDto;
import com.cloud.security.entity.SysUserSecurity;
import com.cloud.security.service.SysUserSecurityService;
import com.cloud.zuul.util.DateTimeUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cloud.security.service.proxy.*;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


/**
 * 用户密码管理服务
 * @author hzwy23@163.com
 * @since 0.0.1
 * */
@Slf4j
@Service
public class SysUserSecurityServiceImpl implements SysUserSecurityService {

    @Autowired
    private ApiAuthRoutesService userService;

    @Autowired
    private SysUserSecurityDao sysUserSecurityDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ResultBody register(String username, String password, String mobilePhone, String code) {


        // 调用系统管理服务，新增用户
        ResultBody message =  userService.register(username, mobilePhone);
        if (message.getCode() != RestCodeEnum.SUCCESS.getCode()) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        SysUserDomainDto element = new Gson().fromJson(message.getData().toString(), SysUserDomainDto.class);

        log.debug("register return message is: {}, user info is: {}", message, element);


        // 保存用户密码信息
        SysUserSecurity item = new SysUserSecurity();
        item.setUsername(username);
        item.setUserId(element.getId());
        item.setPassword(passwordEncoder.encode(password));
        item.setEnable(true);
        item.setDelFlag(0);
        String currentTime = DateTimeUtil.dateformat(System.currentTimeMillis());
        item.setCreateTime(currentTime);
        item.setUpdateTime(currentTime);
        try {
            sysUserSecurityDao.save(item);
        } catch (DataIntegrityViolationException e){
            log.info("{}账号已经存在", username);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultBody.success(10001,"账号已经存在",username);
        }

        return ResultBody.success("Success");
    }

    @Override
    public ResultBody updatePhone(int userId, String mobilePhone, String operator) {
        return null;
    }

    @Override
    public ResultBody delete(int userId, String operator) {
        return null;
    }

    @Override
    public ResultBody updatePassword(int userId, String password, String operator) {
        return null;
    }

    @Override
    public ResultBody changeStatus(int userId, int status, String operator) {
        return null;
    }
}
