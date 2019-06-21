package com.smallflyingleg.sso.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.smallflyingleg.sso.pojo.SysUser;
import com.smallflyingleg.sso.pojo.SysUserRole;
import com.smallflyingleg.sso.service.SysUserRoleService;
import com.smallflyingleg.sso.service.SysUserService;
import com.smallflyingleg.sso.vo.AuthorityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用于加载用户信息 实现UserDetailsService接口，或者实现AuthenticationUserDetailsService接口
 *
 * @author ChengLi
 */

public class CustomUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken>{

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

        //实现AuthenticationUserDetailsService，实现loadUserDetails方法
      /*  implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {


    }*/

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        System.out.println("当前的用户名是：" + token.getName());
        //*这里我为了方便，就直接返回一个用户信息，实际当中这里修改为查询数据库或者调用服务什么的来获取用户信息*//*

        // 用户信息
        SysUser sysUser = sysUserService.selectOne(new EntityWrapper<SysUser>().eq("user_name", token.getName()));
        // 角色信息
        Set<SysUserRole> sysUserRoleList = sysUserRoleService.querySysUserRoleByUserId(sysUser.getUserId());

        Set<AuthorityInfo> authorities = new HashSet<AuthorityInfo>();

        AuthorityInfo authorityInfo;
        for(SysUserRole sysUserRole : sysUserRoleList){
            authorityInfo = new AuthorityInfo(sysUserRole.getRoleName());  //权限 sysUserRole.getRoleName()需要修改
            authorities.add(authorityInfo);
        }
        sysUser.setAuthorities(authorities);
        return sysUser;
    }

    /**
     * loadUserByUsername : 获取用户信息(用户名，密码，角色集)
     *
     * 返回UserDetails，这是一个接口，通常返回它的字类org.springframework.security.core.userdetails.User
     * User的构造需要三个参数：用户名，密码，角色集
     *
     * @author wdx
     * @version 1.0
     * @date 2019/6/21 14:36
     * @return org.springframework.security.core.userdetails.UserDetails
     * @since JDK 1.8
     */
   /* @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 用户信息
        SysUser sysUser = sysUserService.selectOne(new EntityWrapper<SysUser>().eq("user_name", username));
        // 角色信息
        List<SysUserRole> sysUserRoleList = sysUserRoleService.querySysUserRoleByUserId(sysUser.getUserId());

        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        SimpleGrantedAuthority simpleGrantedAuthority;
        for(SysUserRole sysUserRole : sysUserRoleList){
            simpleGrantedAuthority = new SimpleGrantedAuthority(sysUserRole.getRoleName());
            authorities.add(simpleGrantedAuthority);
        }

        return new User(sysUser.getUsername(), sysUser.getPassword(), authorities);

    }*/
}
