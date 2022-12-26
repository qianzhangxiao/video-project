package com.qzx.user.configs.author;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzx.user.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义权限不足处理类
 * @author Linky
 */
@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 权限不足处理
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        log.error("权限异常--{}", exception.getMessage());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        new ObjectMapper().writeValue(response.getOutputStream(), ResponseResult.error(500,"权限不足"));
        outputStream.flush();
        outputStream.close();
    }

}
