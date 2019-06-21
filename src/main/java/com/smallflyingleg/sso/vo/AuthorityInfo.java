package com.smallflyingleg.sso.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * 权限信息
 * wdx
 */
@Data
@AllArgsConstructor
public class AuthorityInfo implements GrantedAuthority {
    /**
     * 权限CODE
     */
    private String authority;

}
