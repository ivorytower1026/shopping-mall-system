<template>
  <div>
    <div class="feedbackClass">
      <el-card class="box-card">
        <h3>帮助</h3>
        <div v-for="(t, i) in helpsText" :key="i" class="text item">
          {{ i + 1 }}. {{ t }}
        </div>
      </el-card>

      <h3 style="margin-bottom: 20px">反馈</h3>
      <textarea
        name=""
        id=""
        cols="60"
        rows="12"
        placeholder="请输入反馈内容"
      ></textarea>
      <el-button type="primary" style="margin-top: 20px">提交</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Help",
  data() {
    return {
      helpsText: [
        "如果遇到麻烦，请先百度",
        "如果百度也不能解决，就打管理员电话",
        "管理员电话不知道？那就没办法了",
      ],
    };
  },
};
</script>

<style scoped>
.feedbackClass {
  position: absolute;
  float: left;
  margin-left: 30px;
  width: 300px;
}
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 480px;
  margin-bottom: 60px;
}
</style>
