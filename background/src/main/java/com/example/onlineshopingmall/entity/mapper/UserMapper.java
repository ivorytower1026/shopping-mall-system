package com.example.onlineshopingmall.entity.mapper;

import com.example.onlineshopingmall.entity.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86130
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-10-03 09:48:52
* @Entity com.example.onlineshopingmall.entity.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




