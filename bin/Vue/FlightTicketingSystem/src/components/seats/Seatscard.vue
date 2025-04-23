<template>
  <v-card border="opacity-50 sm" class="border-xl font-weight-bold big-text">
    <v-card-text>
      <v-row
        justify="space-between"
        align="center"
        class="font-weight-bold text-h3"
        style="height: 40px"
      >
        <v-col>
          <div>{{ selectseat.seatNumber }}</div>
        </v-col>
        <v-col>
          <div>{{ selectseat.seatclasname }}</div>
        </v-col>
        <v-col>
          <div>{{ selectseat.price }}元</div>
        </v-col>
      </v-row>
    </v-card-text>
    <v-card-actions style="display: flex; justify-content: flex-end">
      <!-- 顯示取消座位 -->
      <v-btn
        v-if="actionType === 'cancel'"
        color="#F44336"
        outlined
        @click="useSeatStore().toggleSeat(selectseat)"
      >
        <v-icon left>mdi-close</v-icon>
        取消座位
      </v-btn>
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
defineProps(["selectseat", "actionType"]);
</script>

<style scoped></style>
