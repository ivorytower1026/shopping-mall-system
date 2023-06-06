package com.example.onlineshopingmall.entity.mapper;

import com.example.onlineshopingmall.entity.domain.GoodsCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86130
* @description 针对表【goods_category(商品类别)】的数据库操作Mapper
* @createDate 2022-10-14 19:45:10
* @Entity com.example.onlineshopingmall.entity.domain.GoodsCategory
*/
@Mapper
public interface GoodsCategoryMapper extends BaseMapper<GoodsCategory> {

}




