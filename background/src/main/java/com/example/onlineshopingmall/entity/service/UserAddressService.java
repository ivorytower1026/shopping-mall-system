package com.example.onlineshopingmall.entity.service;

import com.example.onlineshopingmall.entity.domain.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author 86130
* @description 针对表【user_address】的数据库操作Service
* @createDate 2022-10-07 13:01:05
*/
@Service
public interface UserAddressService extends IService<UserAddress> {
    public void saveUserAddress(UserAddress userAddress);
    public void updateUserAddress(UserAddress userAddress);
}
