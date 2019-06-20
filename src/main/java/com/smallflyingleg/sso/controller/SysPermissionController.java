package com.smallflyingleg.sso.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smallflyingleg.sso.service.SysPermissionService;
import com.smallflyingleg.sso.pojo.SysPermission;
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
@RequestMapping("/sysPermission")
public class SysPermissionController {


    @Autowired
    private SysPermissionService sysPermissionService;


    /**
     * 获取数据列表 (分页)
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/sysPermission")
    public Page<SysPermission> findListByPage(@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        Page<SysPermission> page = sysPermissionService.selectPage(new Page<>(currentPage,pageSize));
        return page;
    }


    /**
     *
     * 根据id获取单个数据
     * @param id
     * @return
     */
    @GetMapping("/sysPermission/{id}")
    public SysPermission findOneById(@PathVariable("id") Long id){
        SysPermission SysPermission = sysPermissionService.selectById(id);
        return SysPermission;
    }


    /**
     *
     * 添加数据
     * @param SysPermission
     * @return
     */
    @PostMapping("/sysPermission")
    public Boolean add(@RequestBody SysPermission SysPermission){
        boolean isOk = sysPermissionService.insert(SysPermission);
        return isOk;
    }


    /**
     *
     * 更新数据
     * @param SysPermission
     * @return
     */
    @PutMapping("/sysPermission")
    public Boolean update(@RequestBody SysPermission SysPermission){
        boolean isOk = sysPermissionService.updateById(SysPermission);
        return isOk;
    }

    /**
     *
     * 删除数据
     * @param ids
     * @return
     */
    @DeleteMapping("/sysPermission")
    public Boolean delete(@RequestParam("ids") List<Long> ids){
        boolean isOk = sysPermissionService.deleteBatchIds(ids);
        return isOk;
    }




}

