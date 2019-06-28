package com.smallflyingleg.sso.service.impl;

import com.smallflyingleg.sso.pojo.OrderItem;
import com.smallflyingleg.sso.mapper.OrderItemMapper;
import com.smallflyingleg.sso.service.OrderItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wdx
 * @since 2019-06-25
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
