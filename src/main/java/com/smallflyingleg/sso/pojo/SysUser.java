package com.smallflyingleg.sso.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.baomidou.mybatisplus.annotations.Version;

import com.smallflyingleg.sso.vo.AuthorityInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.velocity.util.introspection.Uberspect;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 
 * </p>
 *
 * @author wdx
 * @since 2019-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sf_sys_user")
public class SysUser extends Model<SysUser> implements UserDetails {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    /**
     * 账号
     */
    @TableField(value = "user_name")
    private String username;
    /**
     * 姓名
     */
    private String fullName;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * qq关联
     */
    private String openId;

    @TableField(exist = false)
    private boolean isAccountNonExpired = true;

    @TableField(exist = false)
    private boolean isAccountNonLocked = true;

    @TableField(exist = false)
    private boolean isCredentialsNonExpired = true;

    @TableField(exist = false)
    private boolean isEnabled = true;

    @TableField(exist = false)
    private Set<AuthorityInfo> authorities = new HashSet<AuthorityInfo>();




    @Override
    protected Serializable pkVal() {
        return this.userId;
    }


}
