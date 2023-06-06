import Vue from 'vue'
import App from './App.vue'
import store from "./store/index"
Vue.config.productionTip = false
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueRouter from 'vue-router';
import router from "./router/index.js"
Vue.use(ElementUI);
Vue.use(VueRouter)

const vm = new Vue({
  el: "#app",
  render: h => h(App),
  store,
  router: router,
  beforeCreate() {
    Vue.prototype.$bus = this
  }

})

export default vm


