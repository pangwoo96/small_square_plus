<!-- src/components/user/LoginForm.vue -->

<template>
  <form @submit.prevent="handleLogin">
    <div>
      <label for="username">아이디: </label>
      <input id="username" v-model="username" type="text" required/>
    </div>
    <div>
      <label for="password">비밀번호: </label>
      <input id="password" v-model="password" type="password" required>
    </div>
    <button type="submit">로그인</button>
  </form>
</template>

<script setup lang="ts">

import {ref} from "vue";
import axios from "axios";
import router from "@/router";

const username = ref('');
const password = ref('');

// async: 항상 Promise 객체를 반환
// await: Promise가 완료될 때까지 기다림, 그리고 그 결과값을 변수에 담는다.

const handleLogin = async () => {
  try {
    const response = await axios.post("http://localhost:8080/api/users/login", {
      // 앞의 username, password는 백엔드에서 응답을 보낼 때의 필드명과 동일해야함
      // 뒤에 username, password는 <template>에서의 v-model의 변수명
      username: username.value,
      password: password.value
    })

    const accessToken = response.data.accesstoken;
    const refreshToken = response.data.refreshToken;

    localStorage.setItem('accessToken', 'Bearer ' + accessToken)
    localStorage.setItem('refreshToken', refreshToken)

    router.push('/')

    console.log("로그인 성공", response.data)
  } catch (error) {
    console.log("로그인 실패", error)
  }
}
</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 15px;
  width: 300px;
}

input {
  padding: 8px;
}

button {
  padding: 10px;
}
</style>