import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'


axios.defaults.baseURL = "http://localhost:8080"; // 配置后端接口的基础 URL
axios.defaults.withCredentials = true; // 如果有跨域和登录需要

// 请求拦截器
axios.interceptors.request.use((config) => {
  const adminData = JSON.parse(localStorage.getItem("adminData"));
  if (adminData) {
    config.headers.Authorization = `Bearer ${adminData.token || ""}`;
  }
  return config;
});


// 响应拦截器
axios.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem("adminData");
      router.push("/login");
    }
    return Promise.reject(error);
  }
);

Vue.config.productionTip = false
Vue.use(ElementUI);
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

// app.vue写在script里面  main.js写在app挂在完之后
const debounce = (fn, delay) => {
  let timer
  return (...args) => {
    if (timer) {
      clearTimeout(timer)
    }
    timer = setTimeout(() => {
      fn(...args)
    }, delay)
  }
}

const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
  constructor(callback) {
    callback = debounce(callback, 200);
    super(callback);
  }
}
