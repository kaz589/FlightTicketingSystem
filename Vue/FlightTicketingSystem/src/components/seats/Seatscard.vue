<template>
  <v-card class="border-xl">
    <v-card-text>
      <label>價格:{{ selectseat.price }}</label>
      <label>座位號碼:{{ selectseat.seatNumber }}</label>
      <label>艙等:{{ selectseat.seatclasname }}</label>
    </v-card-text>
    <v-card-actions style="display: flex; justify-content: flex-end">
      <!-- 顯示取消座位 -->
      <v-btn
        v-if="actionType === 'cancel'"
        color="#F44336"
        text="取消座位"
        @click="cancelSeat"
      ></v-btn>
      <!-- 顯示刪除座位 -->
      <v-btn
        v-if="actionType === 'delete'"
        color="#F44336"
        text="刪除座位"
        @click="deleteSeat"
      ></v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup>
// import { defineProps } from "vue";
import { useRouter } from "vue-router"; // 引入 vue-router
import { useSeatStore } from "@/stores/useSeatStore";
const router = useRouter(); // 使用 vue-router
defineProps(["selectseat","actionType"]);
</script>

<style scoped></style>
