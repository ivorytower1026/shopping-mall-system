package com.example.onlineshopingmall.entity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshopingmall.entity.domain.User;
import com.example.onlineshopingmall.entity.service.UserService;
import com.example.onlineshopingmall.entity.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 86130
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-10-03 09:48:52
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




