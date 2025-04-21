<template>

  <v-card border="opacity-50 sm" class="border-xl font-weight-bold big-text">
    <v-card-text>
      <v-row
        justify="space-between"
        align="center"
        class="font-weight-bold text-h6"
      >
        <v-col class="text-center">
          <div>{{ flight.originAirport }}</div>
          <div>{{ flight.departureTime }}</div>
        </v-col>
        <v-col class="text-center">
          <div>預估里程:{{ flight.estimatedDistance }}</div>
          <div class="line-arrow-wrap">
            <div class="line"></div>
            <span class="mdi mdi-arrow-right-thin"></span>
          </div>
        </v-col>
        <v-col class="text-center">
          <div>{{ flight.destinationAirport }}</div>
          <div>{{ flight.arrivalTime }}</div>
        </v-col>
      </v-row>
    </v-card-text>
    <v-card-actions style="display: flex; justify-content: flex-end">
      <v-btn
        color="#2196F3"
        text="選擇座位"
        @click="$router.push('seatSelection?flightid=' + flight.id)"
      ></v-btn>
    </v-card-actions>
  </v-card>

</template>

<script setup>
// import { defineProps } from "vue";
import { useRouter } from "vue-router"; // 引入 vue-router

defineProps(["flight"]);
</script>


<style scoped>
.line-arrow-wrap {
  position: relative;
  width: 100%;         /* 線條+箭頭總寬，依需求調整 */
  margin: 0 auto;     /* 置中於父容器 */
  height: 40px;       /* 至少大於箭頭高度 */

  display: flex;
  align-items: center;
}

.line {
  flex: 1;                    /* 占滿剩餘空間 */
  height: 5px;
  background-color: black;
}

.mdi-arrow-right-thin {
  font-size: 40px;
  color: black;
  margin-left: 1px;           /* 緊貼線條，可調整間距 */
  display: inline-block;
  transform: scaleX(2);       /* 如果想讓箭頭變長 */
}
</style>
