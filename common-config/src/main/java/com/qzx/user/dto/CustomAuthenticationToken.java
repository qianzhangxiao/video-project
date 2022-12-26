package com.qzx.user.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private Integer type;

    public CustomAuthenticationToken(Object principal, Object credentials,Integer type){
        super(principal,credentials);
        this.type = type;
    }

    public CustomAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public CustomAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public Integer getType() {
        return type;
    }
}
