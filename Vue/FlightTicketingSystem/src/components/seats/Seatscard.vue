<template>
    <v-card  class="border-xl">
         <v-card-text>
            <label>價格:{{ selectseat.price }}</label>
            <label>座位號碼:{{ selectseat.seatNumber }}</label>
            <label>艙等:{{ selectseat.seatclasname }}</label>
         </v-card-text>
        
       </v-card>
</template>

<script setup>
// import { defineProps } from "vue";
import { useRouter } from "vue-router"; // 引入 vue-router
const router = useRouter(); // 使用 vue-router
defineProps(["selectseat"]);
</script>

<style  scoped>


</style>