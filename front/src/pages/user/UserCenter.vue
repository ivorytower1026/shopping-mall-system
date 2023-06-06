<template>
  <div style="margin-top: 50px">
    <el-menu
      router
      style="float: left; width: 200px; height: 1000px; margin-right: 30px"
      :default-active="$route.path"
      class="el-menu-vertical-demo"
    >
      <el-menu-item index="/user/UserCenter/UserInformation">
        <i class="el-icon-user"></i>
        <span slot="title">个人信息</span>
      </el-menu-item>

      <el-menu-item index="/user/UserCenter/UserPosition">
        <i class="el-icon-position"></i>
        <span slot="title">收货地址</span>
      </el-menu-item>

      <el-menu-item index="/user/UserCenter/UserHelp">
        <i class="el-icon-edit-outline"></i>
        <span slot="title">帮助与反馈</span>
      </el-menu-item>
    </el-menu>

    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "UserCenter",
};
</script>

<style>
</style>