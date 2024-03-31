import Vue from 'vue'
import App from './App.vue'
// 引入element ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

// 引入路由
import VueRouter from "vue-router";
import router from './router';
Vue.use(VueRouter);

// 引入store
import store from './store';


// 引入css
import './assets/global.css';
// 引入axios
import axios from "axios";
Vue.prototype.$axios = axios;

Vue.prototype.$httpUrl='http://localhost:8082'

Vue.config.productionTip = false
Vue.use(VueRouter);
Vue.use(ElementUI);

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
