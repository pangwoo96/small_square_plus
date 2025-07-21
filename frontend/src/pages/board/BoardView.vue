<script setup lang="ts">

import axios from "axios";
import {ref} from "vue";
import DropZone from "@/components/board/DropZone.vue";

interface FileMeta {
  fileName: string;
  fileSize: number;
  fileType: string;
}

const files = ref<FileMeta[]>([]);

const handleCreate = () => {
  const body = {
    fileList: files.value // fileList는 내가 정의한 이름 -> BE에서 DTO의 필드 이름
  }
  console.log("files : " + files);
  console.log("fileList : " + fileList.value);
  axios.post("/api/posts/", body)
      .then((res) => {
        console.log("성공: " + res.data);
      })
      .catch((err) => {
        console.error("에러발생", err);
      })
}

</script>

<template>
  <div>
    <DropZone v-model:files="files" />
    <button @click="handleCreate">[create]</button>
  </div>
</template>