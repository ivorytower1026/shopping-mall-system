package com.example.onlineshopingmall.entity.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user_cart
 */
@TableName(value ="user_cart")
@Data
public class UserCart implements Serializable {
    /**
     * 
     */
    @TableId
    private String cartId;

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
    private String addrId;

    /**
     * 
     */
    private Integer goodsQuantity;

    /**
     * 
     */
    private String goodsName;
    private String goodsPic;

    /**
     * 
     */
    private BigDecimal totalPrice;
    private BigDecimal subPrice;


    private String consigneePhone;

    /**
     *
     */
    private String address;

    /**
     *
     */
    private String consigneeName;
    /**
     * 
     */
    private Integer isDeleted;

    /**
     * 1表示在售 2表示售空 3表示停售
     */
    private Integer goodsStatus;
    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     *
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}