import store from '@/router/index'
import VueRouter from 'vue-router'

import vm from "../main"



const router = new VueRouter({
    routes: [
        //用户相关操作
        {
            path: "/user",
            component: () => import("@/components/user/userDisplay.vue"),

            redirect: "/user/ShoppingMall",
            children: [
                //根据具体的商品id搜寻跳转到的新页面
                {
                    path: "GoodsDetailed",
                    component: () => import("@/pages/user/goodsDetailed/GoodsDetailed.vue"),
                    meta: {
                        title: "商品详情页"
                    }
                },
                {
                    path: "ShoppingMall",
                    query: {
                        keyWord: ""
                    },
                    component: () => import("@/pages/user/ShoppingMall.vue"),
                    meta: { title: "商城中心" },
                },
                {
                    path: "UserCart",
                    component: () => import("@/pages/user/UserCart.vue"),
                    meta: {
                        loginAuth: true,
                        title: "购物车"
                    }
                },
                {
                    path: "UserOrder",
                    component: () => import("@/pages/user/UserOrder.vue"),
                    meta: {
                        loginAuth: true,
                        title: "订单"
                    }
                },
                // 用户个人中心
                {
                    path: "UserCenter",
                    component: () => import("@/pages/user/UserCenter.vue"),
                    meta: {
                        loginAuth: true,
                        title: "个人中心"
                    },
                    redirect: "/user/UserCenter/UserInformation",
                    children: [
                        {
                            path: "UserInformation",
                            component: () => import("@/pages/user/userCenter/UserInformation.vue"),
                            meta: {
                                loginAuth: true,
                                title: "个人信息"
                            },
                        },
                        {
                            path: "UserPosition",
                            component: () => import("@/pages/user/userCenter/UserPosition.vue"),
                            meta: {
                                loginAuth: true,
                                title: "收货地址"
                            },
                        },
                        {
                            path: "UserHelp",
                            component: () => import("@/pages/user/userCenter/UserHelp.vue"),
                            meta: {
                                loginAuth: true,
                                title: "帮助与反馈"
                            },
                        },

                    ]
                }
            ],
            // }
        },

        //商户界面
        {
            path: "/merchant",
            component: () => import("@/components/merchant/MerchantDisplay.vue"),
            redirect: "/merchant/info",
            children: [

                {
                    path: "info",
                    component: () => import("@/pages/merchant/Info.vue"),
                    meta: {
                        title: "商户信息",
                    }
                },
                {
                    path: "order",
                    component: () => import("@/pages/merchant/Order.vue"),
                    meta: {
                        title: "订单信息",
                    }
                },
                {
                    path: "shop",
                    component: () => import("@/pages/merchant/Shop.vue"),
                    meta: {
                        title: "我的店铺",
                    }
                },
                {
                    path: "help",
                    component: () => import("@/pages/merchant/Help.vue"),
                    meta: {
                        title: "帮助与反馈",
                    }
                },
            ]
        },
        //商户添加商品界面
        {
            path: "/merchant/addGoods",
            component: () => import("@/pages/merchant/goods_operation/AddGoods.vue"),
            meta: {
                title: "添加商品",
            }

        },

        //管理员界面
        {
            path: "/administrator",
            component: () => import("@/components/backManage/AdministratorDisplay.vue"),
            redirect: "/administrator/manageUser",
            children: [
                {
                    path: "manageUser",
                    component: () => import("@/pages/administrator/ManageUser.vue"),
                    meta: {
                        title: "管理用户信息",
                    }
                },
                {
                    path: "manageMerchant",
                    component: () => import("@/pages/administrator/ManageMerchant.vue"),
                    meta: {
                        title: "管理商户信息",
                    }
                },
            ]
        },

        //用户注册操作(用的是用户UserSign.vue组件)
        {
            path: "/UserSign",
            component: () => import("@/pages/user/UserSign.vue"),
            meta: { title: "注册" }
        },


        //三者共同注册界面
        {
            path: "/Sign",
            component: () => import("@/components/start/Sign.vue"),
            meta: { title: "注册" }
        },

        //登录主界面
        {
            path: "/",
            component: () => import("@/components/start/Start.vue")
        },
        //三者登录共同界面
        {
            path: "/Login",
            component: () => import("@/components/start/Login.vue")
        },

    ]
})

// router.beforeEach((to, from, next) => {
//     if (to.meta.loginAuth === true) {
//         if (store.state.loginSuccess === true) {
//             next()
//         }
//     } else {
//         next()
//     }
// })


router.afterEach((to, from) => {
    document.title = to.meta.title || "网上商城系统";
})


//提示框
function prompt(str) {
    const h = vm.$createElement;
    vm.$message({
        message: h("p", null, [
            h("span", null, ""),
            h("i", { style: "color: teal" }, str),
        ]),
        // duration: 2000
    });

}



export default router