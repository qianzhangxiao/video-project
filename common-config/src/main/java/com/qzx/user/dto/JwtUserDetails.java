package com.qzx.user.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.qzx.user.entity.VoUserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class JwtUserDetails implements UserDetails {


    private VoUserEntity user;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Boolean enabled;

    @JSONField(serialize = false)  //json格式化排除此数据
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetails(VoUserEntity user){
        this.accountNonExpired = true;
        this.accountNonLocked = user.getStatus() == 3;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.user = user;
        if (!ObjectUtils.isEmpty(user.getRoleList())){
            this.authorities = user.getRoleList().stream().map(res -> new SimpleGrantedAuthority(res.getRoleCode())).collect(Collectors.toSet());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserCode();
    }

    /**账号是否未过期*/
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**账号是否未锁定，默认是false*/
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /** 账号凭证是否未过期，默认是false，记得还要改一下*/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
