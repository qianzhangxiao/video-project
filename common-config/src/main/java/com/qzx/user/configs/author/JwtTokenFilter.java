package com.qzx.user.configs.author;

import com.alibaba.fastjson2.JSON;
import com.qzx.user.utils.Constant;
import com.qzx.user.dto.CustomAuthenticationToken;
import com.qzx.user.dto.JwtUserDetails;
import com.qzx.user.entity.VoUserEntity;
import com.qzx.user.utils.JwtTokenUtil;
import com.qzx.user.utils.RedisUtil;
import com.qzx.user.utils.SecurityUserUtil;
import io.seata.common.util.StringUtils;
import io.seata.core.context.RootContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**令牌过滤器*/
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        handleSeataXid(request);
        final String token = request.getHeader("token");
        if (ObjectUtils.isEmpty(token)){
            filterChain.doFilter(request,response);
            return;
        }
        /**解析token*/
        String redisKey= Constant.USER_LOGIN_TOKEN+JwtTokenUtil.getUsername(token);
        /**获取用户信息*/
        if (redisUtil.hasKey(redisKey)){
            VoUserEntity user = JSON.parseObject(redisUtil.get(redisKey).toString(), VoUserEntity.class);
            JwtUserDetails userDetails = new JwtUserDetails(user);
            /**刷新redis缓存时间*/
            redisUtil.expire(redisKey,30*60);
            /**ThreadLocal保存用户信息*/
            SecurityUserUtil.setUserInfo(user);
            SecurityContextHolder.getContext().setAuthentication(new CustomAuthenticationToken(userDetails,null,userDetails.getAuthorities()));
        }
        filterChain.doFilter(request,response);
    }

    /**
     * 处理seata配置
     * */
    private void handleSeataXid(HttpServletRequest request){
        String xid = RootContext.getXID();
        String rpcXid = request.getHeader("TX_XID");
        if (StringUtils.isBlank(xid) && StringUtils.isNotBlank(rpcXid)) {
            RootContext.bind(rpcXid);
            log.info("rpcXid=====>{}",rpcXid);
        }
    }

}
