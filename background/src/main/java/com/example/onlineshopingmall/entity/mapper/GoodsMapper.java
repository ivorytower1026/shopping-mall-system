package com.example.onlineshopingmall.entity.mapper;

import com.example.onlineshopingmall.entity.domain.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86130
* @description 针对表【goods(商品)】的数据库操作Mapper
* @createDate 2022-10-16 09:18:25
* @Entity com.example.onlineshopingmall.entity.domain.Goods
*/
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

}




