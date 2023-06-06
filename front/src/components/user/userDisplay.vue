<template>
  <div>
    <el-menu
      router
      :default-active="$route.path"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
    >
      <el-menu-item
        index="/user/ShoppingMall"
        style="float: left; margin-left: 100px"
      >
        商城中心
      </el-menu-item>

      <div class="search">
        <el-input placeholder="请输入要查询的商品" v-model="keyword">
          <el-button
            slot="append"
            icon="el-icon-search"
            @click="searchByKeyWord"
          ></el-button>
        </el-input>
      </div>
      <div
        style="float: right; margin: 10px 20px"
        v-show="this.$store.state.loginSuccess"
      >
        <el-popover
          placement="top-start"
          title="退出登录"
          width="60px"
          trigger="hover"
        >
          <el-button
            circle
            slot="reference"
            icon="el-icon-switch-button"
            @click="logout"
          >
          </el-button>
        </el-popover>
      </div>

      <el-menu-item
        index="/user/UserCenter"
        style="float: right; margin-right: 50px"
      >
        个人中心</el-menu-item
      >
      <el-menu-item
        index="/user/UserCart"
        style="float: right; margin-right: 30px"
      >
        购物车
      </el-menu-item>
      <el-menu-item
        index="/user/UserOrder"
        style="float: right; margin-right: 30px"
      >
        订单信息</el-menu-item
      >
    </el-menu>
    <router-view></router-view>
    <div v-if="this.$store.state.isLogin" class="mask"></div>
    <user-login v-show="this.$store.state.isLogin" />
  </div>
</template>

<script>
import UserLogin from "./UserLogin.vue";
export default {
  components: { UserLogin },
  data() {
    return {
      keyword: "",
    };
  },
  methods: {
    handleSelect(index) {
      console.log(index);
    },
    logout() {
      localStorage.removeItem("user_id");
      localStorage.removeItem("user_token");
      this.$store.commit("set_loginSuccess", false);
      this.$message.success("已退出登录");
      this.$store.commit("set_active_index", "1");
      this.$router.push({
        path: "/user/ShoppingMall",
      });
    },
    //查找按钮被点击
    searchByKeyWord() {
      if(this.keyword.trim()!==""){
        this.$router.push({
          path:"/user/ShoppingMall",
          query:{
            keyword:this.keyword
          }
        })
      }
    },
  },
};
</script>

<style>
.mask {
  background-color: rgb(0, 0, 0);
  opacity: 0.3;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
}
.search {
  display: inline;
  float: left;
  margin-top: 10px;
  margin-left: 20%;
  width: 400px;
}
</style>