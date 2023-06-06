package com.example.onlineshopingmall.entity.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName administrator
 */
@TableName(value ="administrator")
@Data
public class Administrator implements Serializable {
    /**
     * 
     */
    @TableId
    private String administratorId;

    //邀请码
    private String invitationCode;

    /**
     * 
     */
    private String administratorName;
    private String phone;

    private String password;
    /**
     * 
     */
    //管理员头像图片位置
    private String administratorHeadPic;

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