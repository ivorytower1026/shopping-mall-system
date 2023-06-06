package com.example.onlineshopingmall.entity.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user_cart_bind_merchant
 */
@TableName(value ="user_cart_bind_merchant")
@Data
public class UserCartBindMerchant implements Serializable {
    /**
     * 
     */
    @TableId
    private String userCartBindMerchantId;

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