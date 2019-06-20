package com.smallflyingleg.sso.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smallflyingleg.sso.service.SysUserService;
import com.smallflyingleg.sso.pojo.SysUser;
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
@RequestMapping("/sysUser")
public class SysUserController {


    @Autowired
    private SysUserService sysUserService;


    /**
     * 获取数据列表 (分页)
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/sysUser")
    public Page<SysUser> findListByPage(@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        Page<SysUser> page = sysUserService.selectPage(new Page<>(currentPage,pageSize));
        return page;
    }


    /**
     *
     * 根据id获取单个数据
     * @param id
     * @return
     */
    @GetMapping("/sysUser/{id}")
    public SysUser findOneById(@PathVariable("id") Long id){
        SysUser SysUser = sysUserService.selectById(id);
        return SysUser;
    }


    /**
     *
     * 添加数据
     * @param SysUser
     * @return
     */
    @PostMapping("/sysUser")
    public Boolean add(@RequestBody SysUser SysUser){
        boolean isOk = sysUserService.insert(SysUser);
        return isOk;
    }


    /**
     *
     * 更新数据
     * @param SysUser
     * @return
     */
    @PutMapping("/sysUser")
    public Boolean update(@RequestBody SysUser SysUser){
        boolean isOk = sysUserService.updateById(SysUser);
        return isOk;
    }

    /**
     *
     * 删除数据
     * @param ids
     * @return
     */
    @DeleteMapping("/sysUser")
    public Boolean delete(@RequestParam("ids") List<Long> ids){
        boolean isOk = sysUserService.deleteBatchIds(ids);
        return isOk;
    }




}

