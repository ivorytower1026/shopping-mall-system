package com.example.onlineshopingmall.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onlineshopingmall.common.BaseContext;
import com.example.onlineshopingmall.common.R;
import com.example.onlineshopingmall.dto.CartListDto;
import com.example.onlineshopingmall.entity.domain.*;
import com.example.onlineshopingmall.entity.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;



@RequestMapping("/user/cart")
@RestController
@Slf4j
public class UserCartController {
    @Autowired
    UserCartService userCartService;
    @Autowired
    UserCartBindMerchantService userCartBindMerchantService;
    @Autowired
    MerchantService merchantService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    UserAddressService userAddressService;
    //添加商品到购物车
    @PostMapping("/postGoodsInCart")
    public R<String> postGoodsInCart(@RequestBody UserCart userCart){
        String userId = BaseContext.getSessionId();
        userCart.setUserId(userId);
        userCartService.saveInCartAndUpdateTime(userCart);
        return R.success("yes");
    }
    //获取购物车数据
    //一个current就是找三个merchantId下的购物车
    @GetMapping("/getUserCart")
    public R<Map> getUserCart(@RequestParam("current")Long current, @RequestParam("size")Long size){

        String userId = BaseContext.getSessionId();
        Page<UserCartBindMerchant> userCartBindMerchantPage = new Page<>(current, size);
        LambdaQueryWrapper<UserCartBindMerchant> userCartBindMerchantLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userCartBindMerchantLambdaQueryWrapper.orderByDesc(UserCartBindMerchant::getUpdateTime);
        userCartBindMerchantLambdaQueryWrapper.eq(UserCartBindMerchant::getUserId,userId);
        userCartBindMerchantService.page(userCartBindMerchantPage,userCartBindMerchantLambdaQueryWrapper);
        List<UserCartBindMerchant> userCartBindMerchantPageRecords = userCartBindMerchantPage.getRecords();
        log.info("userCartBindMerchantPageRecords={}",userCartBindMerchantPageRecords);
        //存储cartListDtos
        ArrayList<CartListDto> cartListDtos = new ArrayList<>();
        //将查出来的merchantId转化为一个cartListDto对象
        for (UserCartBindMerchant ucbm : userCartBindMerchantPageRecords) {
            LambdaQueryWrapper<UserCart> userCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userCartLambdaQueryWrapper.eq(UserCart::getMerchantId,ucbm.getMerchantId()).eq(UserCart::getUserId,userId);
            List<UserCart> list = userCartService.list(userCartLambdaQueryWrapper);

            CartListDto cartListDto = new CartListDto();
            log.info("ucbm={}",ucbm);
            LambdaQueryWrapper<Merchant> merchantLambdaQueryWrapper = new LambdaQueryWrapper<>();
            merchantLambdaQueryWrapper.eq(Merchant::getMerchantId,ucbm.getMerchantId());
            Merchant merchant = merchantService.getOne(merchantLambdaQueryWrapper);
            log.info("merchant={}",merchant);
            cartListDto.setShopName(merchant.getShopName());
            cartListDto.setCartList(list);
            //查询最新的goods状态
            LambdaQueryWrapper<Goods> goodsLambdaQueryWrapper = new LambdaQueryWrapper<>();
            LambdaQueryWrapper<UserAddress> userAddressLambdaQueryWrapper = new LambdaQueryWrapper<>();
            for (UserCart userCart : list) {
                //更改现在的cartListDto中的userCart所有有关该goodsId的信息 ,全部都给它改了 效率极低
                goodsLambdaQueryWrapper.eq(Goods::getGoodsId,userCart.getGoodsId());
                Goods goods = goodsService.getOne(goodsLambdaQueryWrapper);
                userCart.setGoodsStatus(goods.getGoodsStatus());
                userCart.setGoodsName(goods.getGoodsName());
                userCart.setSubPrice(goods.getSubPrice());
                userCart.setTotalPrice(goods.getSubPrice().multiply(new BigDecimal(userCart.getGoodsQuantity())));
                if (userCart.getGoodsStatus()!=1){
                    cartListDto.setUsable(true);
                }
                //更改与addrId相关的所有信息
                userAddressLambdaQueryWrapper.eq(UserAddress::getAddrId,userCart.getAddrId());
                UserAddress userAddress = userAddressService.getOne(userAddressLambdaQueryWrapper);
                userCart.setConsigneeName(userAddress.getConsigneeName());
                userCart.setConsigneePhone(userAddress.getConsigneePhone());
                userCart.setAddress(userAddress.getAddress());
                userCartService.updateById(userCart);
                goodsLambdaQueryWrapper.clear();
                userAddressLambdaQueryWrapper.clear();
            }
            cartListDtos.add(cartListDto);
        }
        HashMap<String, Object> map = new HashMap<>();

        map.put("groupByMerchantIdCart",cartListDtos);
        map.put("total",userCartBindMerchantPage.getTotal() );
        return R.success(map);
    }

    //修改购物车
    @PostMapping("/postUpdateUserCart")
    public R<String> postUpdateUserCart(@RequestBody UserCart userCart){
        LambdaQueryWrapper<UserCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCart::getCartId,userCart.getCartId());
        userCartService.updateById(userCart);
        return R.success("ok");
    }
    //删除购物车中的某条数据
    @DeleteMapping("/deleteCartByCartId")
    public R<String> deleteCartByCartId(@RequestParam("cartId")String cartId){
        //删除前看看UserCartBindMerchant中还有没有与该商户
        //绑定的数据，若没有也将其删除
        LambdaQueryWrapper<UserCart> userCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userCartLambdaQueryWrapper.eq(UserCart::getCartId,cartId);
        UserCart userCartOne = userCartService.getOne(userCartLambdaQueryWrapper);
        userCartService.removeById(cartId);



        //判断userCart还有没有其他的userId和merchantId绑定的购物车数据
        userCartLambdaQueryWrapper.clear();
        userCartLambdaQueryWrapper.eq(UserCart::getUserId,userCartOne.getUserId()).eq(UserCart::getMerchantId,userCartOne.getMerchantId());
        List<UserCart> userCartList = userCartService.list(userCartLambdaQueryWrapper);
        if(userCartList.size()==0){
            //说明cart中还有别的绑定数据
            LambdaQueryWrapper<UserCartBindMerchant> userCartBindMerchantLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userCartBindMerchantLambdaQueryWrapper.eq(UserCartBindMerchant::getUserId, userCartOne.getUserId()).eq(UserCartBindMerchant::getMerchantId, userCartOne.getMerchantId());
            userCartBindMerchantService.remove(userCartBindMerchantLambdaQueryWrapper);
        }

        return  R.success("ok");
    }
    //删除与商家绑定的购物车
    @DeleteMapping("/deleteCartGroupByMerchantId")
    public R<String> deleteCartGroupByMerchantId(@RequestBody UserCart []userCarts){
        String merchantId =userCarts[0].getMerchantId();
        String userId = userCarts[0].getUserId();
        for (UserCart uc : userCarts) {
            userCartService.removeById(uc);
        }
        LambdaQueryWrapper<UserCartBindMerchant> userCartBindMerchantLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userCartBindMerchantLambdaQueryWrapper.eq(UserCartBindMerchant::getUserId, userId).eq(UserCartBindMerchant::getMerchantId, merchantId);
        userCartBindMerchantService.remove(userCartBindMerchantLambdaQueryWrapper);
        return R.success("ok");
    }
}
