// src/main.ts

import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from "./router"; // 라우터 import

createApp(App).mount('#app')

const app = createApp(App)
app.use(router)
app.mount('#app')
