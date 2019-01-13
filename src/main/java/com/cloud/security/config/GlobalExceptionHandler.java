package com.cloud.security.config;


import com.cloud.common.RestCodeEnum;
import com.cloud.common.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultBody globalException(Exception e) {
        log.error("未处理异常, 异常信息时：{}", e.getMessage());
        ResultBody resultBody = ResultBody.success(RestCodeEnum.UNHANDLED_EXCEPTION, e.getStackTrace());
        return resultBody;
    }
}
