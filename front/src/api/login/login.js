import { request } from "@/request/request"

//获取用户登录信息
// export function loginInfo(params) {
//     return request({
//         url: '/user/loginInfo',
//         // headers: {
//         //     'Content-Type': 'multipart/form-data',
//         // },
//         method: 'get',
//         //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
//         params
//     })
// }




//用户注册
export function usersign(data) {
    return request({
        url: '/user/usersign',
        headers: {
            // 'Content-Encoding': 'UTF-8',
            'Content-Type': 'application/json;charset=UTF-8',
        },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        data
    })
}

//用户登录
export function userlogin(params) {
    return request({
        url: '/user/userlogin',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'post',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}