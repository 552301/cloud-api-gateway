package com.cloud.zuul.service;

import com.cloud.common.ResultBody;
import com.cloud.zuul.entity.ZuulWhiteList;

public interface ZuulWhiteListService {

    ResultBody add(ZuulWhiteList param);

    ResultBody delete(Integer id);

    ResultBody update(ZuulWhiteList param);

    ResultBody findALl();

    boolean isWhitelist(String method, String path);

}
