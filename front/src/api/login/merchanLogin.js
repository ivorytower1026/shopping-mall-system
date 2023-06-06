import { request } from "@/request/request"

//商户注册(需要返回merchantId和merchantToke)
export function merchantSign(params) {
    return request({
        url: '/merchant/merchantSign',
        headers: {
            // 'Content-Encoding': 'UTF-8',
            'Content-Type': 'application/json;charset=UTF-8',
        },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
//商户登录(需要返回merchantId和merchantToke)
export function merchantLogin(params) {
    return request({
        url: '/merchant/merchantLogin',
        headers: {
            // 'Content-Encoding': 'UTF-8',
            'Content-Type': 'application/json;charset=UTF-8',
        },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
