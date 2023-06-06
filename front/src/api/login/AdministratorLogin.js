import { request } from "@/request/request"

//管理员注册(需要返回administratorId和administratorToke)
export function administratorSign(params) {
    return request({
        url: '/administrator/administratorSign',
        headers: {
            // 'Content-Encoding': 'UTF-8',
            'Content-Type': 'application/json;charset=UTF-8',
        },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
//管理员登录(需要返回administratorId和administratorToke)
export function administratorLogin(params) {
    return request({
        url: '/administrator/administratorLogin',
        headers: {
            // 'Content-Encoding': 'UTF-8',
            'Content-Type': 'application/json;charset=UTF-8',
        },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
