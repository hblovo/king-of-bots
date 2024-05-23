import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'bootstrap/dist/css/bootstrap.min.css'
// 引入Bootstrap Icons CSS
import 'bootstrap-icons/font/bootstrap-icons.css'
createApp(App).use(store).use(router).mount('#app')
