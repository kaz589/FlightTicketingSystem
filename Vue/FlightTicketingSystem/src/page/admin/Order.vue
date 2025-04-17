<template>
    <v-container class="pa-4">
      <v-row>
        <v-col cols="12">
          <h1 class="text-h5">訂單管理</h1>
          <v-btn color="primary" @click="handleClick">點我</v-btn>
          <p v-if="showMessage" class="mt-4">你點了按鈕！</p>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  
  const showMessage = ref(false)
  
  const handleClick = () => {
    showMessage.value = true
  }
  </script>
  
  <style scoped>
  </style>
  