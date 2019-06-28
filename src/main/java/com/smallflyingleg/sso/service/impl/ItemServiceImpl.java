package com.smallflyingleg.sso.service.impl;

import com.smallflyingleg.sso.pojo.Item;
import com.smallflyingleg.sso.mapper.ItemMapper;
import com.smallflyingleg.sso.service.ItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author wdx
 * @since 2019-06-25
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

}
