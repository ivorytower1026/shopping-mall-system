package com.example.onlineshopingmall.entity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshopingmall.entity.domain.UserCart;
import com.example.onlineshopingmall.entity.domain.UserCartBindMerchant;
import com.example.onlineshopingmall.entity.service.UserCartBindMerchantService;
import com.example.onlineshopingmall.entity.service.UserCartService;
import com.example.onlineshopingmall.entity.mapper.UserCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 86130
* @description 针对表【user_cart】的数据库操作Service实现
* @createDate 2022-10-27 10:54:03
*/
@Service
public class UserCartServiceImpl extends ServiceImpl<UserCartMapper, UserCart>
    implements UserCartService{
    @Autowired
    UserCartBindMerchantService userCartBindMerchantService;

    //存到数据库之前要更改userCartBindMerchant表中的updateTime

    @Transactional
    @Override
    public void saveInCartAndUpdateTime(UserCart userCart) {
        String merchantId = userCart.getMerchantId();
        UserCartBindMerchant userCartBindMerchant = new UserCartBindMerchant();
        userCartBindMerchant.setUserId(userCart.getUserId());
        userCartBindMerchant.setMerchantId(merchantId);
        //若UserCartBindMerchant有该merchantId 就修改其updateTime
        LambdaQueryWrapper<UserCartBindMerchant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCartBindMerchant::getMerchantId,merchantId).eq(UserCartBindMerchant::getUserId,userCart.getUserId());
        UserCartBindMerchant one = userCartBindMerchantService.getOne(wrapper);
        if (one!=null){
            one.setUpdateTime(LocalDateTime.now());
            userCartBindMerchantService.updateById(one);
        }else {
            //若没有就添加该条记录

            userCartBindMerchantService.save(userCartBindMerchant );
        }

        //判断其购物车是否本来就有这件商品，有的话就修改数量
        LambdaQueryWrapper<UserCart> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(UserCart::getGoodsId,userCart.getGoodsId());
        UserCart one1 = getOne(wrapper1);
        if (one1!=null){
            int num = userCart.getGoodsQuantity() + one1.getGoodsQuantity();
            userCart.setGoodsQuantity(num);
            BigDecimal subPrice = userCart.getSubPrice();
            userCart.setTotalPrice(subPrice.multiply(new BigDecimal(num)));
            update(userCart,wrapper1);
        }else {
            save(userCart);
        }
    }
}




