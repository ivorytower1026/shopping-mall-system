package com.example.onlineshopingmall.entity.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId
    private String userId;

    /**
     * 
     */
    private String userName;

    /**
     * 
     */
    private String phone;

    private String password;

    /**
     * 
     */
    private String userHeadPicName;

    /**
     * 性别 1表示男     2表示女
     */
    private Integer sex;

    /**
     * 
     */
    private Integer age;
    /**
     * 爱好
     */
    private String hobbys;

    /**
     * 
     */
    private Integer isDeleted;
    private Integer isUsable;

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