//商户用

import { request } from "@/request/request"

//获取商户登录信息
export function getMerchantInfo(params) {
    return request({
        url: '/merchant/getMerchantInfo',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
//修改商户信息
export function postMerchantInfo(data) {
    return request({
        url: '/merchant/postMerchantInfo',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        data
    })
}

//修改商户电话
export function postMerchantPhone(params) {
    return request({
        url: '/merchant/postMerchantPhone',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}

//获取商户订单
export function getGoodsOrder(params) {
    return request({
        url: '/merchant/order/getGoodsOrder',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
//修改订单
export function postGoodsOrderStatus(params) {
    return request({
        url: '/merchant/order/postGoodsOrderStatus',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}