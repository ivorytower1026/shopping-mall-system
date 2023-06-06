package com.example.onlineshopingmall.entity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshopingmall.entity.domain.Goods;
import com.example.onlineshopingmall.entity.service.GoodsService;
import com.example.onlineshopingmall.entity.mapper.GoodsMapper;
import org.springframework.stereotype.Service;

/**
* @author 86130
* @description 针对表【goods(商品)】的数据库操作Service实现
* @createDate 2022-10-16 09:18:25
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements GoodsService{

}




