<template>
  <RouterView></RouterView>
</template>

<script setup>
import { onMounted } from "vue";
import { useAuthStore } from "./stores/auth";

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
</style>
