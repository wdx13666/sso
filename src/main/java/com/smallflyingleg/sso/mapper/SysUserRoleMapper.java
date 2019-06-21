package com.smallflyingleg.sso.mapper;

import com.smallflyingleg.sso.pojo.SysUser;
import com.smallflyingleg.sso.pojo.SysUserRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wdx
 * @since 2019-06-19
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    public Set<SysUserRole> querySysUserRoleByUserId(@Param("userId")Long userId);
}
