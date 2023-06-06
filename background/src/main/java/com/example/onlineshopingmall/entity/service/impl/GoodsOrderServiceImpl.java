package com.example.onlineshopingmall.entity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshopingmall.entity.domain.GoodsOrder;
import com.example.onlineshopingmall.entity.domain.UserCart;
import com.example.onlineshopingmall.entity.domain.UserCartBindMerchant;
import com.example.onlineshopingmall.entity.service.GoodsOrderService;
import com.example.onlineshopingmall.entity.mapper.GoodsOrderMapper;
import com.example.onlineshopingmall.entity.service.UserAddressService;
import com.example.onlineshopingmall.entity.service.UserCartBindMerchantService;
import com.example.onlineshopingmall.entity.service.UserCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 86130
 * @description 针对表【goods_order】的数据库操作Service实现
 * @createDate 2022-10-27 19:59:51
 */

@Service
public class GoodsOrderServiceImpl extends ServiceImpl<GoodsOrderMapper, GoodsOrder>
        implements GoodsOrderService {

    @Autowired
    UserCartService userCartService;
    @Autowired
    UserCartBindMerchantService userCartBindMerchantService;

    @Transactional
    @Override
    public void saveGoodsToGoodsOrder(UserCart[] userCarts) {
        List<UserCart> userCartsList = Arrays.asList(userCarts);
        LambdaQueryWrapper<UserCart> ucLQW = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<UserCartBindMerchant> ucbmLQW = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<GoodsOrder> goLQW = new LambdaQueryWrapper<>();

        for (UserCart uc : userCarts) {
            //1.删除通过cartId删除用户购物车中的有关数据
            userCartService.removeById(uc);
            //2.判断用户购物车中与该商户商品是否还有，没有就将user_cart_bind_merchant中的有关数据删除
            ucLQW.eq(UserCart::getUserId, uc.getUserId()).eq(UserCart::getMerchantId, uc.getMerchantId());
            List<UserCart> list = userCartService.list(ucLQW);
            //如果没有用户与该商户绑定的数据，就直接删除
            if (list.size() == 0) {
                ucbmLQW.eq(UserCartBindMerchant::getUserId, uc.getUserId()).eq(UserCartBindMerchant::getMerchantId, uc.getMerchantId());
                userCartBindMerchantService.remove(ucbmLQW);
            }
            //3.再将该次请求的数据加入到订单中
            GoodsOrder goodsOrder = new GoodsOrder();
            BeanUtils.copyProperties(uc, goodsOrder, "cartId","updateTime");
//            设置订单状态为 1未支付
            goodsOrder.setOrderStatus(1);
            save(goodsOrder);

            ucLQW.clear();
            ucbmLQW.clear();
            goLQW.clear();
        }
    }
}




