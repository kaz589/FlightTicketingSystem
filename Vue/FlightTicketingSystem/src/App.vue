<template>
  <RouterView></RouterView>
</template>

<script setup>
import { onMounted } from "vue";
import { useAuthStore } from "./stores/auth";
import '@/assets/css/bordered-table.css'

const authStore = useAuthStore();

onMounted(() => {
  // 應用啟動時檢查並恢復登入狀態
  authStore.checkLoginStatus();
});
</script>

<style>
.my-swal-popup {
  z-index: 200000000 !important;
}
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.4s;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
.swal2-container {
  z-index: 20000 !important;
}
</style>
