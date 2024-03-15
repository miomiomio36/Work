import App from './App.vue'
import router from './router'
import store from './store'
// import ViewIndexVue from './views/ViewIndex.vue'

import { createApp } from 'vue'

import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';
import http from './api/http.js';
import axios from 'axios';

const app = createApp(App);
// app.config.productionTip = false
axios.defaults.baseURL = "http://localhost:8888"


app.config.globalProperties.$axios = axios


// app.prototype.$http = http
app.config.globalProperties.$http = http
app.use(store).use(router).use(store).use(ElementPlus).mount('#app')


