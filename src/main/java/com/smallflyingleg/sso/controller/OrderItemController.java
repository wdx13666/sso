package com.smallflyingleg.sso.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smallflyingleg.sso.service.OrderItemService;
import com.smallflyingleg.sso.pojo.OrderItem;
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
 * @since 2019-06-25
 */
@RestController
@RequestMapping("/orderItem")
public class OrderItemController {


    @Autowired
    private OrderItemService orderItemService;


    /**
     * 获取数据列表 (分页)
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/orderItem")
    public Page<OrderItem> findListByPage(@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,@RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        Page<OrderItem> page = orderItemService.selectPage(new Page<>(currentPage,pageSize));
        return page;
    }


    /**
     *
     * 根据id获取单个数据
     * @param id
     * @return
     */
    @GetMapping("/orderItem/{id}")
    public OrderItem findOneById(@PathVariable("id") Long id){
        OrderItem OrderItem = orderItemService.selectById(id);
        return OrderItem;
    }


    /**
     *
     * 添加数据
     * @param OrderItem
     * @return
     */
    @PostMapping("/orderItem")
    public Boolean add(@RequestBody OrderItem OrderItem){
        boolean isOk = orderItemService.insert(OrderItem);
        return isOk;
    }


    /**
     *
     * 更新数据
     * @param OrderItem
     * @return
     */
    @PutMapping("/orderItem")
    public Boolean update(@RequestBody OrderItem OrderItem){
        boolean isOk = orderItemService.updateById(OrderItem);
        return isOk;
    }

    /**
     *
     * 删除数据
     * @param ids
     * @return
     */
    @DeleteMapping("/orderItem")
    public Boolean delete(@RequestParam("ids") List<Long> ids){
        boolean isOk = orderItemService.deleteBatchIds(ids);
        return isOk;
    }




}

