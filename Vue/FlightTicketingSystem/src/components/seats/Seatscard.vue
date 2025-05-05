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
        @click="deleteSeat(selectseat)"
      ></v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup>
 import { defineProps } from "vue";
import { useRouter } from "vue-router"; // 引入 vue-router
import { useSeatStore } from "@/stores/useSeatStore";
import Swal from "sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";
import { ApiSeats } from "@/utils/API";
const router = useRouter(); // 使用 vue-router
const emit = defineEmits(['deleted'])
defineProps(["selectseat", "actionType"]);
async function deleteSeat(selectseat){
  console.log(selectseat);
  const result = await Swal.fire({
    title: "確認要送出訂單嗎？",
    text: "送出後將進入付款流程，請確認座位及金額正確。",
    icon: "question",
    showCancelButton: true,
    confirmButtonText: "確認送出",
    cancelButtonText: "取消",
    customClass: {
      popup: 'my-swal-popup'
    }
  });
  if (result.isConfirmed) {
    await ApiSeats.releaseSeat(selectseat.seatId).then((res) => {
      console.log(res.data);
    });
    emit("deleted", selectseat.seatId); // 通知父元件
  }
}
</script>

<style scoped>
</style>