//获取商品用

import { request } from "@/request/request"

//获取商品信息
export function getGoodsInfo(params) {
    return request({
        url: '/goods/getGoodsInfo',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
//获取具体的商品信息
export function getGoodsInfoById(params) {
    return request({
        url: '/goods/getGoodsInfoById',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}