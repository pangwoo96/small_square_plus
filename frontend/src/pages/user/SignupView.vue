<!-- src/views/SignupView.vue -->

<template>
  <div>
    <h2>회원가입 페이지</h2>
    <SignupForm />
  </div>
</template>

<script setup lang="ts">
import SignupForm from "@/components/user/SignupForm.vue";
</script>

<style scoped>
h2 {
  color: darkred;
  margin-bottom: 20px;
}
</style>