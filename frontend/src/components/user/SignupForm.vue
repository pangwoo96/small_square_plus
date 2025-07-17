<!-- src/components/user/SignupForm.vue -->

<template>
  <form @submit.prevent="handleSignUp">
    <div>
      <label for="username">아이디: </label>
      <input id="username" v-model="username" type="text" required/>
    </div>
    <div>
      <label for="password">비밀번호: </label>
      <input id="password" v-model="password" type="password" required>
    </div>
    <div>
      <label for="nickname">닉네임: </label>
      <input id="nickname" v-model="nickname" type="text" required>
    </div>
    <div>
      <label for="email">이메일: </label>
      <input id="email" v-model="email" type="email" required>
    </div>
    <div>
      <label for="name">이름: </label>
      <input id="name" v-model="name" type="text" required>
    </div>
    <button type="submit">회원가입</button>
  </form>
</template>

<script setup lang="ts">

import axios from "axios";
import {ref} from "vue";
import router from "@/router";

const username = ref('')
const password = ref('')
const nickname = ref('')
const email = ref('')
const name = ref('')

const handleSignUp = async () => {
  try {
    const response = await axios.post("http://localhost:8080/api/users/", {
      username: username.value,
      password: password.value,
      nickname: nickname.value,
      email: email.value,
      name: name.value
    });

    await router.push("/");

  } catch (error) {
    console.log("회원가입 실패")
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