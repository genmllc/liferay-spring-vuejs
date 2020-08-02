import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

Vue.config.productionTip = false;

console.log('Hello, is anyone here ?');

new Vue({
  router,
  store,
  render: h => h(App),
  mounted() {
    this.$router.replace('/')
  }
}).$mount("#app");
