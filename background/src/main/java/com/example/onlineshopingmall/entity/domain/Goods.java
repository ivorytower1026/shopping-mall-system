package com.example.onlineshopingmall.entity.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 商品
 * @TableName goods
 */
@TableName(value ="goods")
@Data
public class Goods implements Serializable {
    /**
     * 
     */
    @TableId
    private String goodsId;

    /**
     * 
     */
    private String goodsCategoryId;

    /**
     * 
     */
    private String merchantId;

    /**
     * 
     */
    private String goodsName;

    /**
     * 商品价格
     */
    private BigDecimal price;
    private BigDecimal subPrice;
    /**
     * 商品描述
     */
    private String goodsDescription;
    /**
     * 商品数量
     */
    private Long quantity;

    /**
     * 1表示在售 2表示售空 3表示停售
     */
    private Integer goodsStatus;

    /**
     * 图片路径列表，可设置多张图片，用空格隔开
     */
    private String goodsPic;


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