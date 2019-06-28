package com.smallflyingleg.sso.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wdx
 * @since 2019-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sf_order_item")
public class OrderItem extends Model<OrderItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商品id
     */
    private Long itemId;
    /**
     * SPU_ID
     */
    private Long goodsId;
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 商品标题
     */
    private String title;
    /**
     * 商品单价
     */
    private BigDecimal price;
    /**
     * 商品购买数量
     */
    private Integer num;
    /**
     * 商品总金额
     */
    private BigDecimal totalFee;
    /**
     * 商品图片地址
     */
    private String picPath;
    private String sellerId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
