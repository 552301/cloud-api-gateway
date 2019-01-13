package com.cloud.security.filter;

import com.cloud.common.ResultBody;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.cloud.common.RestCodeEnum.ACCESS_DENIED;


@Configuration
@Slf4j
public class CustomAccessDeniedFilter implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        log.info("access denied");
        Gson gson = new Gson();
        ResultBody resultBody = ResultBody.success(ACCESS_DENIED, "无效的Token信息");
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.print(gson.toJson(resultBody));
        printWriter.close();
    }
}
