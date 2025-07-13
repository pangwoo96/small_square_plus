// src/router/index.ts
import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/pages/Home.vue'
import LoginView from "@/pages/user/LoginView.vue";

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/login',
        name: "Login",
        component: LoginView
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router