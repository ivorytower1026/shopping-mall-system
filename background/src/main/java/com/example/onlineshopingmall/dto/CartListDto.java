package com.example.onlineshopingmall.dto;

import com.example.onlineshopingmall.entity.domain.UserCart;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartListDto {
    private String shopName;
    private List<UserCart> cartList;
    private List<UserCart> cartListChecked = new ArrayList<>();
    private boolean checkAll = false;
    //该全选按钮是否禁用
    private boolean usable = false;
}
