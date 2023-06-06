//用户个人中心用

import { request } from "@/request/request"
//-------------------------------------------------用户信息---------------------------------------------------
//获取用户登录信息
export function getUserProfile(params) {
    return request({
        url: '/user/userCenter/getUserProfile',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
//修改用户个人信息
export function postUserProfile(data) {
    return request({
        url: '/user/userCenter/postUserProfile',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        data
    })
}

//更改手机号的请求
export function postUserPhone(params) {
    return request({
        url: '/user/userCenter/postUserPhone',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}


//新增用户地址
export function postUserAddress(data) {
    return request({
        url: '/user/userCenter/postUserAddress',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        data
    })
}
//获取用户地址
export function getUserAddress() {
    return request({
        url: '/user/userCenter/getUserAddress',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx

    })
}
//删除一条地址信息
export function deleteUserAddress(params) {
    return request({
        url: '/user/userCenter/deleteUserAddress',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'delete',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}



//添加商品到购物车
export function postGoodsInCart(data) {
    return request({
        url: '/user/cart/postGoodsInCart',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        data
    })
}

//--------------------------------------------订单---------------------------------------------------
//提交订单
export function postGoodsInOrder(data) {
    return request({
        url: '/user/order/postGoodsInOrder',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        data
    })
}
//将用户购物车选中的商品添加到用户的订单列表中
export function postCartGoodsToGoodsOrder(data) {
    return request({
        url: '/user/order/postCartGoodsToGoodsOrder',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        data
    })
}

//获取订单
export function getGoodsOrder(params) {
    return request({
        url: '/user/order/getGoodsOrder',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}

//修改订单状态
export function postGoodsOrderStatus(params) {
    return request({
        url: '/user/order/postGoodsOrderStatus',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}

//删除订单
export function deleteGoodsOrder(params) {
    return request({
        url: '/user/order/deleteGoodsOrder',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'delete',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
















//--------------------------------------------------购物车-----------------------------------------------------------
//获取购物车数据
export function getUserCart(params) {
    return request({
        url: '/user/cart/getUserCart',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
//修改购物车数据
export function postUpdateUserCart(data) {
    return request({
        url: '/user/cart/postUpdateUserCart',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        data
    })
}
//通过cartId删除购物车数据
export function deleteCartByCartId(params) {
    return request({
        url: '/user/cart/deleteCartByCartId',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'delete',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
//通过merchant删除购物车数据
export function deleteCartGroupByMerchantId(data) {
    return request({
        url: '/user/cart/deleteCartGroupByMerchantId',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'delete',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        data
    })
}


