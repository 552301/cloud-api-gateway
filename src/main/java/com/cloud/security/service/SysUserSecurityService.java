package com.cloud.security.service;

import com.cloud.common.ResultBody;

public interface SysUserSecurityService {


    /**
     * 用户注册
     * @param username 账号
     * @param password 密码
     * @param mobilePhone 手机号
     * @param code 手机验证码
     * */
    ResultBody register(String username, String password, String mobilePhone, String code);


    /**
     * 更新手机号
     * @param userId 账号唯一ID
     * @param mobilePhone 手机号
     * @param operator 操作人
     * */
    ResultBody updatePhone(int userId, String mobilePhone, String operator);


    /**
     * 删除用户
     * @param userId 账号唯一ID
     * @param operator 操作人
     * */
    ResultBody delete(int userId, String operator);

    /**
     * 修改密码
     * @param userId 用户唯一ID
     * @param password 用户密码
     * */
    ResultBody updatePassword(int userId, String password, String operator);


    /**
     * 禁用/启用账号
     * @param userId 用户唯一ID
     * @param status 状态编码
     * @param operator 操作人
     * */
    ResultBody changeStatus(int userId, int status, String operator);
}
