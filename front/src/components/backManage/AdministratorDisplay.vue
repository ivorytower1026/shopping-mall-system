<template>
  <div>
    <div class="myHeader">
      <div class="myHeaderRight">{{ adName }}</div>
      <div class="myHeaderLeft">图标</div>
    </div>
    <hr />
    <el-menu
      router
      :default-active="$route.path"
      class="el-menu-vertical-demo menu"
    >
      <el-menu-item
        index="/administrator/manageUser"
        class="item"
        style="margin-top: 40px"
      >
        <i class="el-icon-user-solid"></i>
        <span slot="title">用户信息</span>
      </el-menu-item>
      <el-menu-item index="/administrator/manageMerchant" class="item">
        <i class="el-icon-user-solid"></i>
        <span slot="title">商户信息</span>
      </el-menu-item>
    </el-menu>

    <div class="display">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: "AdministratorDisplay",
  data() {
    return {
      adName: "",
    };
  },
  mounted() {
    console.log(this.$route.query.administratorName);
    this.adName = this.$route.query.administratorName;
  },
};
</script>


<style scoped>
.item {
  margin-top: 10px;
}
.myHeaderRight {
  position: absolute;
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
  position: absolute;
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