package com.example.onlineshopingmall.entity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onlineshopingmall.entity.domain.UserAddress;
import com.example.onlineshopingmall.entity.service.UserAddressService;
import com.example.onlineshopingmall.entity.mapper.UserAddressMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 86130
* @description 针对表【user_address】的数据库操作Service实现
* @createDate 2022-10-07 13:01:05
*/
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress>
    implements UserAddressService{
    public void saveUserAddress(UserAddress userAddress){
        if(userAddress.getIsDefault()!=1){
            save(userAddress);
            return;
        }
        setAddrIsDefault(userAddress);
        save(userAddress);
    }

    public void updateUserAddress(UserAddress userAddress){

        if(userAddress.getIsDefault()!=1){
            updateById(userAddress);
            return;
        }
        setAddrIsDefault(userAddress);
        updateById(userAddress);
    }

    private void setAddrIsDefault(UserAddress userAddress) {
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAddress::getUserId,userAddress.getUserId());
        List<UserAddress> list = list(wrapper);
        List<UserAddress> collect = list.stream().map((item) -> {
            item.setIsDefault(0);
            return item;
        }).collect(Collectors.toList());

        System.out.println(collect);
        updateBatchById(collect);

    }
}




