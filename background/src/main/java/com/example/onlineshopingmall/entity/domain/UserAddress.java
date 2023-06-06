package com.example.onlineshopingmall.entity.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user_address
 */
@TableName(value ="user_address")
@Data
public class UserAddress implements Serializable {
    /**
     * 
     */
    @TableId
    private String addrId;

    /**
     * 
     */
    private String userId;

    /**
     * 1表示设为默认地址
     */
    private Integer isDefault;

    /**
     * 收件人手机号
     */
    private String consigneePhone;

    /**
     * 收件人姓名
     */
    private String consigneeName;

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

    /**
     * 省份 城市 区
     */
    private String areaAddress;

    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 地址
     */
    private String address;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}