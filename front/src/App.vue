<template>
  <div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "APP",
  // components: {
  //   UserDisplay,
  // },
  mounted() {},
  created() {
    // 在页面加载时读取sessionStorage
    if (sessionStorage.getItem("store")) {
      this.$store.replaceState(
        Object.assign(
          {},
          this.$store.state,
          JSON.parse(sessionStorage.getItem("store"))
        )
      );
    }
    // 在页面刷新时将store保存到sessionStorage里
    window.addEventListener("beforeunload", () => {
      sessionStorage.setItem("store", JSON.stringify(this.$store.state));
    });
  },
};
</script>

<style >
a {
  color: rgb(81, 78, 78);
}
a:hover {
  text-decoration: none;
  color: rgb(0, 0, 0);
}
a:link {
  text-decoration: none;
}
a:visited {
  text-decoration: none;
}
ul{
  list-style: none;
}
</style>
