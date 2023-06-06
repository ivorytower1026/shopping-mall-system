import axios from 'axios'
import store from "@/store/index"
import vm from "@/main"

export function request(config) {
    // 1.创建axios的实例
    const instance = axios.create({
        // 设置基础的url配置项，这样接口处的url前面就不用写url:'http://127.0.0.1:8000/api/home'，直接写成 url:'/api/home', 就可以了
        baseURL: '/api',
        //设置请求超时时间
        timeout: 5000
    })

    // 2.axios的拦截器，用不到的可以忽略这节
    // 2.1.请求拦截的作用
    instance.interceptors.request.use(config => {

        let request_url = config.url.split("/")[1]
        if (request_url === "user") {
            config.headers.userId = localStorage.getItem("user_id")
            config.headers.token = localStorage.getItem("user_token")
        }
        else if (request_url === "administrator") {
            config.headers.administratorId = localStorage.getItem("administrator_id")
            config.headers.administratorToken = localStorage.getItem("administrator_token")
        }
        else if (request_url === "merchant") {
            config.headers.merchantId = localStorage.getItem("merchant_id")
            config.headers.merchantToken = localStorage.getItem("merchant_token")
        }

        return config
    }, err => {
        console.log('请求拦截err: ' + err);
    })

    // 2.2.响应拦截
    instance.interceptors.response.use(res => {
        //通过type来判断是哪个响应 1~用户 2~商户 3~管理员
        var type = res.headers.type
        if (type === "1") {

            store.commit("set_isLogin", false);
            store.commit("set_loginSuccess", true);

            //用户
        }
        else if (type === '2') {

            //商户
        }
        else if (type === '3') {
            //管理员
        }
        return res.data
    }, err => {
        var type = err.response.headers.type
        console.log(err);
        console.log(err.response.headers.type);

        if (type == '1') {
            store.commit("set_isLogin", true)
            store.commit("set_loginSuccess", false);
        }
        //用户

        if (type === '2') {
            vm.$router.push({
                path: "/Login",
                query: {
                    typeId: type,
                },
            })
            vm.$message.info("请先登录")
            //商户
        }
        else if (type === '3') {
            //管理员
        }
        console.log('响应拦截err: ' + err);
    })

    // 3.发送真正的网络请求
    return instance(config)
}
