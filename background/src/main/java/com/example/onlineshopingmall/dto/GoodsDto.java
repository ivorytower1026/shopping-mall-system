package com.example.onlineshopingmall.dto;

import com.example.onlineshopingmall.entity.domain.Goods;
import lombok.Data;

@Data
public class GoodsDto extends Goods {
    private String goodsCategoryName;
}
