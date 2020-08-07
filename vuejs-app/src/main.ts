import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import axios, { AxiosRequestConfig, AxiosInstance } from 'axios'
import VueCookies from 'vue-cookies'

Vue.use(VueCookies);
Vue.$cookies.config('7d')

Vue.config.productionTip = false;

// Get a CSRF token
axios.get("/o/liferay-spring-vuejs/api/services/csrf", {withCredentials: true});

// Add that token to every request
axios.interceptors.request.use((config: AxiosRequestConfig) => {
  config.headers['X-XSRF-TOKEN'] = Vue.$cookies.get('XSRF-TOKEN');
  return config;
});


new Vue({
  router,
  store,
  render: h => h(App),
  mounted() {
    this.$router.replace('/')
  }
}).$mount("#app");
