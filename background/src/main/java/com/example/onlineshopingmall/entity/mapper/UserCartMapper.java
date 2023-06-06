package com.example.onlineshopingmall.entity.mapper;

import com.example.onlineshopingmall.entity.domain.UserCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86130
* @description 针对表【user_cart】的数据库操作Mapper
* @createDate 2022-10-27 10:54:03
* @Entity com.example.onlineshopingmall.entity.domain.UserCart
*/
@Mapper
public interface UserCartMapper extends BaseMapper<UserCart> {

}




