<script setup lang="ts">
import { ref } from 'vue';
const fileInput = ref<HTMLInputElement | null>(null);

interface FileMeta {
  fileName: string;
  fileSize: number;
  fileType: string;
}

const files = defineModel<FileMeta[]>('files');

const handleDrop = (event: DragEvent) => {
  const fileList = event.dataTransfer?.files;
  if (!fileList) return;
  const newFiles: FileMeta[] = Array.from(fileList).map(f => ({
    fileName: f.name,
    fileSize: f.size,
    fileType: f.type,
  }));
  files.value.push(...newFiles);
};

const handleFileChange = (event: Event) => { // 사용자가 파일 선택창에서 직접 파일을 고른 경우 실행되는 이벤트
  const fileList = (event.target as HTMLInputElement).files;
  if (fileList && fileList.length > 0) {
    const newFiles: FileMeta[] = Array.from(fileList).map(f => ({
      fileName: f.name,
      fileSize: f.size,
      fileType: f.type,
    }));
    files.value.push(...newFiles);
  }
};

const openFileDialog = () => {
  fileInput.value?.click();
};
</script>

<template>
  <div
      class="dropzone"
      @dragover.prevent
      @drop.prevent="handleDrop"
  >
    <p>여기에 이미지를 드래그하거나 클릭하여 업로드하세요</p>
    <input type="file" ref="fileInput" @change="handleFileChange" hidden/>
    <button @click="openFileDialog">파일 선택</button>

<ul>
  <li v-for="(f, index) in files" :key="index">{{ f.fileName }}</li>
</ul>
  </div>
</template>


<style scoped>
.dropzone {
  border: 2px dashed #aaa;
  padding: 20px;
  text-align: center;
  cursor: pointer;
}

.preview img {
  max-width: 200px;
  margin-top: 10px;
}
</style>