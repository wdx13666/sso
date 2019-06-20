package com.smallflyingleg.sso.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smallflyingleg.sso.service.SysRolePermissionService;
import com.smallflyingleg.sso.pojo.SysRolePermission;
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
@RequestMapping("/sysRolePermission")
public class SysRolePermissionController {


    @Autowired
    private SysRolePermissionService sysRolePermissionService;


    /**
     * 获取数据列表 (分页)
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/sysRolePermission")
    public Page<SysRolePermission> findListByPage(@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        Page<SysRolePermission> page = sysRolePermissionService.selectPage(new Page<>(currentPage,pageSize));
        return page;
    }


    /**
     *
     * 根据id获取单个数据
     * @param id
     * @return
     */
    @GetMapping("/sysRolePermission/{id}")
    public SysRolePermission findOneById(@PathVariable("id") Long id){
        SysRolePermission SysRolePermission = sysRolePermissionService.selectById(id);
        return SysRolePermission;
    }


    /**
     *
     * 添加数据
     * @param SysRolePermission
     * @return
     */
    @PostMapping("/sysRolePermission")
    public Boolean add(@RequestBody SysRolePermission SysRolePermission){
        boolean isOk = sysRolePermissionService.insert(SysRolePermission);
        return isOk;
    }


    /**
     *
     * 更新数据
     * @param SysRolePermission
     * @return
     */
    @PutMapping("/sysRolePermission")
    public Boolean update(@RequestBody SysRolePermission SysRolePermission){
        boolean isOk = sysRolePermissionService.updateById(SysRolePermission);
        return isOk;
    }

    /**
     *
     * 删除数据
     * @param ids
     * @return
     */
    @DeleteMapping("/sysRolePermission")
    public Boolean delete(@RequestParam("ids") List<Long> ids){
        boolean isOk = sysRolePermissionService.deleteBatchIds(ids);
        return isOk;
    }




}

