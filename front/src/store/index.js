//该文件用于创建Vuex中最为核心的store
import Vue from 'vue'
//引入Vuex
import Vuex from 'vuex'
//应用Vuex插件
Vue.use(Vuex)
import { getOptions } from "@/js/createProvinceCityOptions"
//准备actions——用于响应组件中的动作
const actions = {
    /* jia(context,value){
        console.log('actions中的jia被调用了')
        context.commit('JIA',value)
    },
    jian(context,value){
        console.log('actions中的jian被调用了')
        context.commit('JIAN',value)
    }, */

}
//准备mutations——用于操作数据（state）
const mutations = {
    // JIA(state, value) {
    //     console.log('mutations中的JIA被调用了')
    //     state.sum += value
    // },
    // JIAN(state, value) {
    //     console.log('mutations中的JIAN被调用了')
    //     state.sum -= value
    // }
    set_isLogin(state, flag) {
        state.isLogin = flag
    },
    set_loginSuccess(state, flag) {
        state.loginSuccess = flag
    },

    set_merchantName(state, str) {
        state.merchantName = str
    }
}
//准备state——用于存储数据
const state = {
    isLogin: false,  //用于是否展示登录界面
    loginSuccess: false, //是否成功登录
    merchantName: "商户名",
    addressOptions: getOptions()
}
//准备getters——用于将state中的数据进行加工
const getters = {


}

//创建并暴露store
export default new Vuex.Store({
    actions,
    mutations,
    state,
    getters
})