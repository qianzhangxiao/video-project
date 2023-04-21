package com.qzx.user.configs.author;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzx.user.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 自定义认证失败处理类
 * @author Linky
 */
@Component
@Slf4j
public class CustomAuthorizeFailedHandler implements AuthenticationEntryPoint, Serializable {

    /**
     * 认证失败处理
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("认证失败--{}", exception.getMessage());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        new ObjectMapper().writeValue(outputStream, ResponseResult.error(401,"认证失败，请先登录"));
        outputStream.flush();
        outputStream.close();
    }

}
