package com.cloud.zuul.vo;


import lombok.Data;

@Data
public class LoginVo {

    private String username;

    private String password;

    private String type;
}
