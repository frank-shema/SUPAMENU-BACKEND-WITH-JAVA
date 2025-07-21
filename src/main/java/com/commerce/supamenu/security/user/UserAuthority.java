package com.commerce.supamenu.security.user;

import com.commerce.supamenu.enums.ERole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;


import java.util.UUID;

public class UserAuthority implements GrantedAuthority {
    @Getter
    public UUID userId;
    public ERole authority;

    public UserAuthority(UUID userId, ERole authority) {
        this.userId = userId;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority.toString();
    }
}
