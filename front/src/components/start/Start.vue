<template>
  <div>
    <img
      :src="bg"
      alt="加载中..."
      width="100%"
      height="100%"
      style="opacity: 0.7"
    />
    <div class="all_btn">
      <el-button
        type="primary"
        class="btn"
        style="background-color: rgb(32, 81, 160)"
        @click="Login(1)"
        >用户登录</el-button
      >
      <el-button
        type="primary"
        class="btn"
        style="background-color: rgb(32, 196, 193)"
        @click="Login(2)"
        >商户登录</el-button
      >
      <el-button type="primary" class="btn" @click="Login(3)"
        >管理员登录</el-button
      >
    </div>
  </div>
</template>

<script>
export default {
  name: "",
  data() {
    return {
      bg: require("../../assets/backgroundImg/3.jpg"),
    };
  },
  methods: {
    Login(value) {
      console.log(value === 1);
      this.$router.push({
        path: "/Login",
        query: {
          typeId: value,
        },
      });
    },
  },
};
</script>

<style scoped>
.btn {
  margin-left: 30px;
  width: 300px;
  height: 50px;
  margin-top: 20px;
  opacity: 1;
}
.all_btn {
  position: fixed;
  top: 200px;
  left: 50%;
  transform: translateX(-50%);
  width: 360px;
  height: 250px;
  background-color: rgba(0, 0, 0, 0.249);
}
</style>