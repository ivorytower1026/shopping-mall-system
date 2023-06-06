import { request } from "@/request/request"

//获取用户信息
export function getUserInfo(params) {
    return request({
        url: '/administrator/getUserInfo',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
//修改用户是否可用
export function updateUserStatus(params) {
    return request({
        url: '/administrator/updateUserStatus',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
//------------------------------------------------商户------------------------------------------
//获取商户信息
export function getMerchantInfo(params) {
    return request({
        url: '/administrator/getMerchantInfo',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
//修改商户是否可用
export function updateMerchantStatus(params) {
    return request({
        url: '/administrator/updateMerchantStatus',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}