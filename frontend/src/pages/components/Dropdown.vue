<script setup lang="ts">
import { ref, defineProps, defineEmits } from 'vue';

const props = defineProps<{
  items: string[];
  modelValue: string;
}>();

const emit = defineEmits<{
  (e: 'update:modelValue', value: string): void;
}>();

const isOpen = ref(false);

const toggleDropdown = () => {
  isOpen.value = !isOpen.value;
};

const selectItem = (item: string) => {
  emit('update:modelValue', item); // 부모로 선택값 전달
  isOpen.value = false;
};
</script>

<template>
  <div class="dropdown">
    <button @click="toggleDropdown">{{ modelValue }}</button>
    <ul v-if="isOpen">
      <li
          v-for="(item, index) in items"
          :key="index"
          @click="selectItem(item)"
      >
        {{ item }}
      </li>
    </ul>
  </div>
</template>

<style scoped>
.dropdown {
  position: relative;
}
ul {
  border: 1px solid #000000;
  padding: 0;
  margin: 0;
  list-style: none;
  position: absolute;
  background: black;
}
li {
  padding: 8px 12px;
  cursor: pointer;
}
li:hover {
  background: #000000;
}
</style>