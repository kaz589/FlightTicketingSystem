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
          <div>預估里程:{{ flight.estimatedDistance }}KM</div>
          <div class="duration-wrapper">
          <div class="d-flex align-center justify-center my-2">
            <div class="divider-line"></div>
            <span class="flight-duration-text">
              {{ getFlightDuration(flight.departureTime, flight.arrivalTime) }}
            </span>
            <div class="divider-line divider-line-arrow"></div>
          </div>
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
import { getFlightDuration } from "@/utils/Date";

defineProps(["flight"]);
</script>

<style scoped>
.duration-wrapper {
  max-width: 400px;
  margin: 0 auto;
  /* 你可依需求調整 max-width */
}
.divider-line {
  flex: 1;
  height: 4px;
  background: black;
  border-radius: 2px;
  opacity: 0.8;
  position: relative;
}
.flight-duration-text {
  display: inline-block;
  min-width: 110px;     /* 依需要調整，也可移除 */
  text-align: center;
  font-weight: bold;
  color: black;
  white-space: nowrap;
  margin: 0 1px;
}
.divider-line-arrow {
  flex: 1;
  position: relative;
  margin-right: 0;
}
.divider-line-arrow::after {
  content: '';
  display: block;
  position: absolute;
  right: -32px;
  top: 50%;
  transform: translateY(-50%);
  width: 40px;
  height: 40px;
  background: url('data:image/svg+xml;utf8,<svg fill="black" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M10 17l5-5-5-5v10z"/></svg>') no-repeat center center;
  background-size: 40px 40px;
}
</style>
