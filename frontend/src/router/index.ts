// src/router/index.ts
import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/pages/Home.vue'
import LoginView from "@/pages/user/LoginView.vue";
import SignupForm from "@/components/user/SignupForm.vue";
import SignupView from "@/pages/user/SignupView.vue";
import BoardView from "@/pages/board/BoardView.vue";

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
    },
    {
        path: '/signup',
        name: "Signup",
        component: SignupView
    },
    {
        path: '/board',
        name: "Board",
        component: BoardView
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router