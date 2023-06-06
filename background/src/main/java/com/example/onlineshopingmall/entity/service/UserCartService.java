package com.example.onlineshopingmall.entity.service;

import com.example.onlineshopingmall.entity.domain.UserCart;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author 86130
* @description 针对表【user_cart】的数据库操作Service
* @createDate 2022-10-27 10:54:03
*/
@Service
public interface UserCartService extends IService<UserCart> {
    public void saveInCartAndUpdateTime(UserCart userCart);
}
