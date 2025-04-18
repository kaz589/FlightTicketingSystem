<template>
    <v-card  class="border-xl">
         <v-card-text>
            <label>價格:{{ selectseat.price }}</label>
            <label>座位號碼:{{ selectseat.seatNumber }}</label>
            <label>艙等:{{ selectseat.seatclasname }}</label>
         </v-card-text>
         <v-card-actions style="display: flex; justify-content: flex-end">
            <v-btn
              color="#F44336"
              text="取消座位"
              @click="useSeatStore().toggleSeat(selectseat)"
            ></v-btn>
          </v-card-actions>
       </v-card>
</template>

<script setup>
// import { defineProps } from "vue";
import { useRouter } from "vue-router"; // 引入 vue-router
import { useSeatStore } from '@/stores/useSeatStore';
const router = useRouter(); // 使用 vue-router
defineProps(["selectseat"]);
</script>

<style  scoped>


</style>