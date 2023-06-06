package com.example.onlineshopingmall.entity.mapper;

import com.example.onlineshopingmall.entity.domain.UserAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86130
* @description 针对表【user_address】的数据库操作Mapper
* @createDate 2022-10-07 13:01:05
* @Entity com.example.onlineshopingmall.entity.domain.UserAddress
*/
@Mapper
public interface UserAddressMapper extends BaseMapper<UserAddress> {

}




