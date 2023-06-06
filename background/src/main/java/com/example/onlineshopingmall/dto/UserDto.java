package com.example.onlineshopingmall.dto;

import com.example.onlineshopingmall.entity.domain.User;
import lombok.Data;

@Data
//用户转换对象
public class UserDto extends User {
    private String code;
}
