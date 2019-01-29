package com.cloud.zuul.service.impl;

import com.cloud.common.RestCodeEnum;
import com.cloud.common.ResultBody;
import com.cloud.zuul.dao.ZuulWhiteListDao;
import com.cloud.zuul.entity.ZuulWhiteList;
import com.cloud.zuul.service.ZuulWhiteListService;
import com.cloud.zuul.vo.ZuulWhiteListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class ZuulWhiteListServiceImpl implements ZuulWhiteListService {

    @Autowired
    private ZuulWhiteListDao zuulWhiteListDao;


    @Override
    public ResultBody add(ZuulWhiteList param) {
        param.setDeleteStatus(0);

        ZuulWhiteList item = zuulWhiteListDao.save(param);
        if (item.getId() != 0) {
            return ResultBody.success(RestCodeEnum.ZUUL_WHITE_LIST_ADD_FAILED);
        }
        return ResultBody.success();
    }

    @Override
    public ResultBody delete(Integer id) {
        int size =  zuulWhiteListDao.logicDelete(id);
        if (size > 0) {
            return ResultBody.success(RestCodeEnum.ZUUL_WHITE_LIST_DELETE_FAILED);
        }
        return ResultBody.success();
    }

    @Override
    public ResultBody update(ZuulWhiteList param) {
        Optional<ZuulWhiteList> item = zuulWhiteListDao.findById(param.getId());
        if (item.isPresent()) {
            ZuulWhiteList element = item.get();
            element.setMethod(param.getMethod());
            element.setDomainId(param.getDomainId());
            element.setEnabled(param.isEnabled());
            element.setPath(param.getPath());
            zuulWhiteListDao.save(element);
        } else {
            return ResultBody.success(RestCodeEnum.ZUUL_WHITE_LIST_NO_DATA);
        }

        return ResultBody.success("Bingo");
    }

    @Override
    public ResultBody findALl() {
        List<ZuulWhiteList> result = zuulWhiteListDao.findAll();
        List<ZuulWhiteListVo> ret = new ArrayList<>();
        if ( !result.isEmpty() ) {
            for(ZuulWhiteList item : result) {
                ZuulWhiteListVo element = new ZuulWhiteListVo();
                BeanUtils.copyProperties(item, element);
                ret.add(element);
            }
        }
        return ResultBody.success(ret);
    }

    @Override
    public boolean isWhitelist(String method, String path) {
        int size = zuulWhiteListDao.findRoute(method, path);
        return  size > 0;
    }
}
