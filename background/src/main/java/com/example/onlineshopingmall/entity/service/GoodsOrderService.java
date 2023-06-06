package com.example.onlineshopingmall.entity.service;

import com.example.onlineshopingmall.entity.domain.GoodsOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.onlineshopingmall.entity.domain.UserCart;
import org.springframework.stereotype.Service;

/**
* @author 86130
* @description 针对表【goods_order】的数据库操作Service
* @createDate 2022-10-27 19:59:51
*/
@Service
public interface GoodsOrderService extends IService<GoodsOrder> {

   public void saveGoodsToGoodsOrder(UserCart[] userCarts);
}
