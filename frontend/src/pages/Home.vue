<!-- src/views/Home.vue -->

<template>
  <div>
    <h1>홈 화면입니다.</h1>
    <p>이 페이지는 메인 페이지입니다.</p>
    <button @click="goToLogin">로그인</button>
    <button @click="goToSignup">회원가입</button>
  </div>
  <div>
    <button @click="goToBoard">게시판</button>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";
import Dropdown from '@/components/board/Dropdown.vue'
import DropZone from "@/components/board/DropZone.vue";
const router = useRouter();

const goToLogin = () => {
  router.push("/login"); // url
}

const goToSignup = () => {
  router.push("/signup") // url
}

const goToBoard = () => {
  router.push("/board")
}

</script>

<style scoped>
h1 {
  color: teal;
}
button {
  margin-top: 20px;
  padding: 10px;
}
</style>