package com.example.onlineshopingmall.entity.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 商品类别
 * @TableName goods_category
 */
@TableName(value ="goods_category")
@Data
public class GoodsCategory implements Serializable {
    /**
     * 
     */
    @TableId
    private String goodsCategoryId;

    /**
     * 
     */
    private String goodsCategoryName;

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