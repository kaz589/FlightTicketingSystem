<template>
  

  <!-- Search Bar -->
  <v-row align="center" justify="center" style="height: 100px;" no-gutters>
<v-col cols="auto">
      <v-autocomplete
        v-model="from"
        :items="airports"
        label="From"
        placeholder="Origin"
        prepend-inner-icon="mdi-airplane-takeoff"
        variant="solo"
        density="comfortable"
        rounded
        hide-details
        clearable
        class="my-autocomplete"
      ></v-autocomplete>
    </v-col>

    <!-- To -->
    <v-col cols="auto">
      <v-autocomplete
        v-model="to"
        :items="airports"
        label="To"
        placeholder="Destination"
        prepend-inner-icon="mdi-airplane-landing"
        variant="solo"
        density="comfortable"
        rounded
        hide-details
        clearable
        class="my-autocomplete"
      ></v-autocomplete>
    </v-col>

    <!-- Date range (one input, two pickers) -->
    <v-col cols="auto">
      <FlatPickr
        v-model="range"
        :config="config"
        placeholder="Start → End"
       class=" my-flatpickr"
      />
    </v-col>
    <!-- Search Button -->
    <v-col cols="auto">
    <button
      class="bg-green-400 hover:bg-green-500 text-white font-bold px-6 py-3 rounded-full"
      @click="search()"
    >
      Search
    </button>
    </v-col>
  </v-row>
  <v-container>
    <v-row align="start" justify="center" cols="1" md="1" >
      <v-col
        v-for="flight in flights"
        :key="flight.id"
        align-self="center"
        cols="7"
        md="7"
        sm="12"
        xs="12"
      >
        <FlightCard :flight="flight"></FlightCard>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { ApiAirport } from "@/utils/API";
import { ApiFlight } from "@/utils/API"; 
import FlatPickr from "vue-flatpickr-component";
import "flatpickr/dist/flatpickr.css";
import FlightCard from "@/components/flight/flightcard.vue";
import { Mandarin as zh } from "flatpickr/dist/l10n/zh.js"; // 中文語言包
const range = ref([]);
const from=ref("");
const to=ref("");
const airports = ref([]);


const config = {
  mode: "range",
  dateFormat: "Y-m-d",
  time_24hr: true,
  locale: zh, // 指定中文
};

onMounted(() => {
  //getdisinairport();
  ApiAirport.DistinctAirportName().then((response) => {
  airports.value = response.data;
});
});
// 搜尋函數
async function search() {
  // 取得起始時間
  const [startTime, endTime] = range.value || [];

  try {
    const response = await ApiFlight.searchFlights(
      from.value,
      to.value,
      startTime,
      endTime,
      null, // 這裡如果有機型可以傳 airplaneModelName
      1,    // page
      10    // size
    );
    flights.value = response.data.content; // 假設回傳格式為 { data: [...] }
    console.log("搜尋航班成功", response.data);
  } catch (error) {
    console.error("搜尋航班失敗", error);
  }
}
const flights = ref([
 
]);
</script>

<style scoped>


.my-autocomplete{
  width: 310px;           /* 較長的寬度 */;
}

.my-flatpickr {
  width: 300px;           /* 較長的寬度 */
  height: 48px;           /* 較小的高度 */
  border-radius: 24px;    /* 高度的一半，或直接 9999px */
  border: 1px solid #18191a; /* gray-300 */
  text-align: left;
  font-size: 1.25rem;     /* 更大字體 */
  padding: 10px 15px;
  box-sizing: border-box;
}
</style>
