<template>
     <v-card  class="border-xl">
          
          <v-card-text>
            <div class="flight-info">
              <div class="departure">
                <label>{{ flight.departureCity }}</label>
                <br />
                <label> {{ flight.departureTime }}</label>
              </div>
              <div class="line"></div>
              <div class="arrival">
                <label> {{ flight.arrivalCity }}</label>
                <br />
                <label> {{ flight.arrivalTime }}</label>
              </div>
            </div>
          </v-card-text>
          <v-card-actions style="display: flex; justify-content: flex-end">
            <v-btn
              color="#2196F3"
              text="選擇座位"
              @click="$router.push('seatSelection?flightid='+flight.flightid)"
            ></v-btn>
          </v-card-actions>
        </v-card>
</template>

<script setup>
// import { defineProps } from "vue";
import { useRouter } from "vue-router"; // 引入 vue-router
const router = useRouter(); // 使用 vue-router
defineProps(["flight"]);
</script>

<style  scoped>

.flight-info {
  display: flex;
  flex-direction: row-reverse;
  justify-content: space-between;
  align-items: center;
}

.departure,
.arrival {
  text-align: center;
}

.line {
  width: 40%; /* Full width line */
  height: 1px;
  background-color: black; /* Line color */
  margin: 10px 0; /* Adjust spacing as needed */
  position: relative; /* 確保可以定位箭頭 */
}
.line::after {
  content: '→'; /* 使用箭頭符號 */
  position: absolute;
  right: -15px; /* 調整箭頭位置 */
  top: -14px; /* 調整箭頭垂直位置 */
  font-size: 20px; /* 調整箭頭大小 */
  color: black; /* 箭頭顏色 */
}
</style>