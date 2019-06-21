package com.smallflyingleg.sso.service.impl;

import com.smallflyingleg.sso.pojo.SysUser;
import com.smallflyingleg.sso.pojo.SysUserRole;
import com.smallflyingleg.sso.mapper.SysUserRoleMapper;
import com.smallflyingleg.sso.service.SysUserRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wdx
 * @since 2019-06-19
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public Set<SysUserRole> querySysUserRoleByUserId(Long userId) {
        return baseMapper.querySysUserRoleByUserId(userId);
    }
}
