import { request } from "@/request/request"
//获取验证码，展示在后台
export function getPhoneCode(params) {
    return request({
        url: '/getPhoneCode',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx
        params
    })
}
