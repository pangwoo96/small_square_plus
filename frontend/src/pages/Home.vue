<!-- src/views/Home.vue -->

<template>
  <div>
    <h1>홈 화면입니다.</h1>
    <p>이 페이지는 메인 페이지입니다.</p>
    <button @click="goToLogin">로그인 하러 가기</button>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";
const router = useRouter();
const goToLogin = () => {
  router.push("/login"); // url
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