package com.smallflyingleg.sso.service;

import com.smallflyingleg.sso.pojo.SysUser;
import com.smallflyingleg.sso.pojo.SysUserRole;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wdx
 * @since 2019-06-19
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    public Set<SysUserRole> querySysUserRoleByUserId(Long userId);

}
