package com.cloud.zuul.service.impl;

import com.cloud.common.ResultBody;
import com.cloud.zuul.dao.cassandra.ZuulForwardLoggerDao;
import com.cloud.zuul.entity.ZuulForwardLogger;
import com.cloud.zuul.service.ZuulForwardLoggerService;
import com.cloud.zuul.util.DateTimeUtil;
import com.cloud.zuul.vo.ZuulForwardLoggerVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ZuulForwardLoggerServiceImpl implements ZuulForwardLoggerService {

    @Autowired
    private ZuulForwardLoggerDao zuulForwardLoggerDao;

    @Override
    public ResultBody findAll() {

        Iterable<ZuulForwardLogger> iterable = zuulForwardLoggerDao.findAll();
        Iterator<ZuulForwardLogger> result = iterable.iterator();
        List<ZuulForwardLoggerVo> ret = new ArrayList<>();

        while (result.hasNext()) {
            ZuulForwardLogger item = result.next();
            // 日期格式转换
            long st = Long.valueOf(item.getStartTime());
            long et = Long.valueOf(item.getEndTime());
            String startTime = DateTimeUtil.dateformat(st);
            String endTime = DateTimeUtil.dateformat(et);

            ZuulForwardLoggerVo vo = new ZuulForwardLoggerVo();
            BeanUtils.copyProperties(item, vo);
            vo.setStartTime(startTime);
            vo.setEndTime(endTime);
            vo.setCostTime(et - st);

            ret.add(vo);
        }
        return ResultBody.success(ret);
    }
}
