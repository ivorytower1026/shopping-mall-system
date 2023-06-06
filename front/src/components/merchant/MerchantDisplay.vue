<template>
  <div>
    <div class="myHeader">
      <div class="myHeaderRight">{{ $store.state.merchantName }}</div>
      <div class="myHeaderLeft">图标</div>
    </div>
    <hr>
    <el-menu
      router
      :default-active="$route.path"
      class="el-menu-vertical-demo menu"
      
    >
      <el-menu-item
        index="/merchant/info"
        class="item"
        style="margin-top: 40px"
      >
        <i class="el-icon-user-solid"></i>
        <span slot="title">商铺信息</span>
      </el-menu-item>
      <el-menu-item index="/merchant/order" class="item">
        <i class="el-icon-s-order"></i>
        <span slot="title">订单信息</span>
      </el-menu-item>
      <el-menu-item index="/merchant/shop" class="item">
        <i class="el-icon-s-shop"></i>
        <span slot="title">店铺商品</span>
      </el-menu-item>
      <el-menu-item index="/merchant/help" class="item">
        <i class="el-icon-edit-outline"></i>
        <span slot="title">帮助与反馈</span>
      </el-menu-item>
    </el-menu>

    <div class="display">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: "MerchantDisplay",
};
</script>

<style scoped>
.item {
  margin-top: 10px;
}
.myHeaderRight {
   position:absolute;
  width: 200px;
  height: 40px;
  top: 13px;
  right: 13px;
  margin: 5px;
  line-height: 40px;
  margin-right: 90px;
  text-align: center;
  float: right;
}
.myHeaderLeft {
  position:absolute;
  width: 100px;
  top: 13px;
  line-height: 40px;
  margin: 5px;
  margin-left: 30px;
  text-align: center;
  float: left;
}
.myHeader {
 
  width: 100%;
  height: 50px;
  background-color: rgb(255, 255, 255);
    color: rgb(93, 185, 222);
    font-size: 16px;
}
.display {
  position: relative;
  top: 40px;
  right: -250px;
  width: 1280px;
  height: 700px;
  padding: 0px;
}

/* 菜单导航栏项 */
.menu {
  position: fixed;
  width: 200px;
  height: 1000px;
}
/* 整个 */
.nav {
  display: inline;
  width: 200px;
}
</style>