package com.smallflyingleg.sso.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smallflyingleg.sso.service.ItemService;
import com.smallflyingleg.sso.pojo.Item;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author wdx
 * @since 2019-06-25
 */
@RestController
@RequestMapping("/item")
public class ItemController {


    @Autowired
    private ItemService itemService;


    /**
     * 获取数据列表 (分页)
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/item")
    public Page<Item> findListByPage(@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        Page<Item> page = itemService.selectPage(new Page<>(currentPage,pageSize));
        return page;
    }


    /**
     *
     * 根据id获取单个数据1
     * @param id
     * @return
     */
    @GetMapping("/item/{id}")
    public Item findOneById(@PathVariable("id") Long id){
        Item Item = itemService.selectById(id);
        return Item;
    }


    /**
     *
     * 添加数据
     * @param Item
     * @return
     */
    @PostMapping("/item")
    public Boolean add(@RequestBody Item Item){
        boolean isOk = itemService.insert(Item);
        return isOk;
    }


    /**
     *
     * 更新数据
     * @param Item
     * @return
     */
    @PutMapping("/item")
    public Boolean update(@RequestBody Item Item){
        boolean isOk = itemService.updateById(Item);
        return isOk;
    }

    /**
     *
     * 删除数据
     * @param ids
     * @return
     */
    @DeleteMapping("/item")
    public Boolean delete(@RequestParam("ids") List<Long> ids){
        boolean isOk = itemService.deleteBatchIds(ids);
        return isOk;
    }




}

