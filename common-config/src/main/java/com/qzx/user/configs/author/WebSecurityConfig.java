package com.qzx.user.configs.author;

import com.qzx.user.configs.SecurityProperties;
import com.qzx.user.service.impl.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WebSecurityConfig {


    private final SecurityProperties securityProperties;

    /**
     * 自定义登录
     * */
    private final CustomAuthenticationProvider authenticationProvider;

    private final JwtTokenFilter jwtTokenFilter;

    private final CustomAuthorizeFailedHandler customAuthorizeFailedHandler;

    private final CustomAccessDeniedHandler accessDeniedHandler;

    /**
     * 认证管理器，登录的时候参数会传给 authenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    @RefreshScope
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //允许跨域
                .cors()
                .and()
                .antMatcher("/**")
                .authorizeRequests(res->{
                    // 不鉴权访问
                    res.antMatchers(securityProperties.getPermitAll()).permitAll();
                    // 匿名访问
                    res.antMatchers(securityProperties.getAnonymous()).anonymous();
                    // 除上面外的所有请求全部需要鉴权认证
                    res.anyRequest().authenticated();
                })
                //认证失败处理
                .exceptionHandling(res->{
                    res.accessDeniedHandler(accessDeniedHandler);
                    res.authenticationEntryPoint(customAuthorizeFailedHandler);
                })
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();


    }
}
