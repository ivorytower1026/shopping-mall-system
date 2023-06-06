package com.example.onlineshopingmall.entity.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName merchant
 */
@TableName(value ="merchant")
@Data
public class Merchant implements Serializable {
    /**
     * 
     */
    @TableId
    private String merchantId;
    private String phone;
    /**
     * 省份 城市 区
     */
    private String areaAddress;

    /**
     * 详细地址
     */
    private String detailedAddress;
    private String password;
    /**
     * 
     */

    /**
     * 
     */
    private String shopName;

    /**
     * 
     */
    private String shopAddress;

    /**
     * 
     */
    private String shopPic;

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
    private Integer isUsable;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}