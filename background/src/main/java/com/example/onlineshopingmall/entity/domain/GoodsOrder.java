package com.example.onlineshopingmall.entity.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName goods_order
 */
@TableName(value ="goods_order")
@Data
public class GoodsOrder implements Serializable {
    /**
     * 
     */
    @TableId
    private String orderId;

    /**
     * 
     */
    private String userId;

    /**
     * 
     */
    private String merchantId;

    /**
     * 
     */
    private String goodsId;

    /**
     * 
     */
    private String consigneePhone;
    private String addrId;
    /**
     *
     */
    private String address;
    private String goodsPic;
    /**
     * 
     */
    private String consigneeName;

    /**
     * 
     */
    private String goodsName;

    /**
     * 
     */
    private Integer goodsQuantity;

    /**
     * 
     */
    private BigDecimal subPrice;

    /**
     * 
     */
    private BigDecimal totalPrice;

    /**
     * 1 未支付  2 待发货 3 待收货 4已完成 5已取消
     */
    private Integer orderStatus;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     *
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}