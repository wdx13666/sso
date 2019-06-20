package com.smallflyingleg.sso.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smallflyingleg.sso.service.SysUserRoleService;
import com.smallflyingleg.sso.pojo.SysUserRole;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wdx
 * @since 2019-06-19
 */
@RestController
@RequestMapping("/sysUserRole")
public class SysUserRoleController {


    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * 获取数据列表 (分页)
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/sysUserRole")
    public Page<SysUserRole> findListByPage(@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        Page<SysUserRole> page = sysUserRoleService.selectPage(new Page<>(currentPage,pageSize));
        return page;
    }


    /**
     *
     * 根据id获取单个数据
     * @param id
     * @return
     */
    @GetMapping("/sysUserRole/{id}")
    public SysUserRole findOneById(@PathVariable("id") Long id){
        SysUserRole SysUserRole = sysUserRoleService.selectById(id);
        return SysUserRole;
    }


    /**
     *
     * 添加数据
     * @param SysUserRole
     * @return
     */
    @PostMapping("/sysUserRole")
    public Boolean add(@RequestBody SysUserRole SysUserRole){
        boolean isOk = sysUserRoleService.insert(SysUserRole);
        return isOk;
    }


    /**
     *
     * 更新数据
     * @param SysUserRole
     * @return
     */
    @PutMapping("/sysUserRole")
    public Boolean update(@RequestBody SysUserRole SysUserRole){
        boolean isOk = sysUserRoleService.updateById(SysUserRole);
        return isOk;
    }

    /**
     *
     * 删除数据
     * @param ids
     * @return
     */
    @DeleteMapping("/sysUserRole")
    public Boolean delete(@RequestParam("ids") List<Long> ids){
        boolean isOk = sysUserRoleService.deleteBatchIds(ids);
        return isOk;
    }




}

