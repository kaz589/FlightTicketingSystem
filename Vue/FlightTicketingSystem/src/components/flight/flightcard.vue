<template>
     <v-card>
          <v-card-title> </v-card-title>
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
.card {
  width: 700px;
  height: 150px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 20px;
}
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
  background-color: #ccc; /* Line color */
  margin: 10px 0; /* Adjust spacing as needed */
}
</style>