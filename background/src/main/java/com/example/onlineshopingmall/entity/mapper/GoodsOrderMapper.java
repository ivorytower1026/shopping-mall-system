package com.example.onlineshopingmall.entity.mapper;

import com.example.onlineshopingmall.entity.domain.GoodsOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86130
* @description 针对表【goods_order】的数据库操作Mapper
* @createDate 2022-10-27 19:59:51
* @Entity com.example.onlineshopingmall.entity.domain.GoodsOrder
*/
@Mapper
public interface GoodsOrderMapper extends BaseMapper<GoodsOrder> {

}




