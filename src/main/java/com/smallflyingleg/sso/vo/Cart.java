package com.smallflyingleg.sso.vo;

import com.smallflyingleg.sso.pojo.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Cart implements Serializable {

    private String sellerId;//商家ID
    private String sellerName;//商家名称
    private List<OrderItem> orderItemList;//购物车明细

}
