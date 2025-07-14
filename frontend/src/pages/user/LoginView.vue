<!-- src/views/LoginView.vue -->

<template>
  <div>
    <h2>로그인 페이지</h2>
    <LoginForm />
  </div>
</template>

<script setup lang="ts">
import LoginForm from '@/components/user/LoginForm.vue'
</script>

<style scoped>
h2 {
  color: navy;
  margin-bottom: 20px;
}
</style>