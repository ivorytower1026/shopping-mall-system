
import { request } from "@/request/request"


//获取商品分类
export function getGoodsCategory() {
    return request({
        url: '/goodsCategory/getGoodsCategory',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        method: 'get',
        //参数 类似于 localhost:8080/user/userInfo?username=xxxxx&token=xxxxx

    })
}
