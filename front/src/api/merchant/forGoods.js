//商户用

import { request } from "@/request/request"

//获取该商户的商品数据
export function getMerchantGoodsInfo(params) {
    return request({
        url: '/merchant/goods/getMerchantGoodsInfo',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}

//添加或修改商品
export function postMerchantGoods(data) {
    return request({
        url: '/merchant/goods/postMerchantGoods',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        data
    })
}
//添加或修改商品
export function downloadGoodsPic(params) {
    return request({
        url: '/merchant/goods/downloadGoodsPic',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}


//删除商品
export function deleteMerchantGoods(params) {
    return request({
        url: '/merchant/goods/deleteMerchantGoods',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'delete',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}


