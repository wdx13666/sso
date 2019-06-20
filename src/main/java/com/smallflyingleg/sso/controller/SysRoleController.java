package com.smallflyingleg.sso.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smallflyingleg.sso.service.SysRoleService;
import com.smallflyingleg.sso.pojo.SysRole;
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
@RequestMapping("/sysRole")
public class SysRoleController {


    @Autowired
    private SysRoleService sysRoleService;


    /**
     * 获取数据列表 (分页)
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/sysRole")
    public Page<SysRole> findListByPage(@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        Page<SysRole> page = sysRoleService.selectPage(new Page<>(currentPage,pageSize));
        return page;
    }


    /**
     *
     * 根据id获取单个数据
     * @param id
     * @return
     */
    @GetMapping("/sysRole/{id}")
    public SysRole findOneById(@PathVariable("id") Long id){
        SysRole SysRole = sysRoleService.selectById(id);
        return SysRole;
    }


    /**
     *
     * 添加数据
     * @param SysRole
     * @return
     */
    @PostMapping("/sysRole")
    public Boolean add(@RequestBody SysRole SysRole){
        boolean isOk = sysRoleService.insert(SysRole);
        return isOk;
    }


    /**
     *
     * 更新数据
     * @param SysRole
     * @return
     */
    @PutMapping("/sysRole")
    public Boolean update(@RequestBody SysRole SysRole){
        boolean isOk = sysRoleService.updateById(SysRole);
        return isOk;
    }

    /**
     *
     * 删除数据
     * @param ids
     * @return
     */
    @DeleteMapping("/sysRole")
    public Boolean delete(@RequestParam("ids") List<Long> ids){
        boolean isOk = sysRoleService.deleteBatchIds(ids);
        return isOk;
    }




}

