<template>
    <v-carousel height="400" show-arrows="hover" cycle hide-delimiter-background>
      <v-carousel-item v-for="(img, i) in images" :key="i">
        <v-img :src="img" height="100%" cover>
          <!-- 可選：圖片上方的文字說明 -->
          
        </v-img>
      </v-carousel-item>
    </v-carousel>
  </template>
  
  <script setup>
  const images = [
    new URL('@/assets/img1.jpg', import.meta.url).href,
    new URL('@/assets/img2.jpg', import.meta.url).href,
    new URL('@/assets/img8.jpg', import.meta.url).href,
    new URL('@/assets/img4.jpg', import.meta.url).href,
    new URL('@/assets/img5.jpg', import.meta.url).href,
  ]
  
 
  </script>
  
  <style scoped>
  </style>