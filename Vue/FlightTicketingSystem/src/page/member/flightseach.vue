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
      class="bg-blue-500 hover:bg-blue-600 text-white font-bold px-6 py-3 rounded-full"
      @click="search()"
    >
      Search
    </button>
    </v-col>
  </v-row>
  <v-container>
    <template v-if="flights && flights.length > 0">
    <transition-group name="slide-fade" tag="div" class="flight-list-column">
      <div
        v-for="(flight, idx) in flights"
        :key="flight.id"
        class="flight-card-block"
        :style="{ transitionDelay: (idx * 120) + 'ms' }"
      >
        <FlightCard :flight="flight"   />
      </div>
    </transition-group>
  </template>
  <template v-else >
    <div class="list-column">
    <carousel/>
    <ExpansionPanels/>
  </div>
  </template>
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
import ExpansionPanels from"@/components/ExpansionPanels.vue"
import carousel from "@/components/carousel.vue";
import { showLoadingSwal, closeLoadingSwal,showSuccessSwal } from '@/utils/swalLoading'
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
    showLoadingSwal();
    console.log("搜尋航班成功", response.data);
  } catch (error) {
    console.error("搜尋航班失敗", error);
  }finally{
    closeLoadingSwal();
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
.flight-list-column {
  display: flex;
  flex-direction: column;
  align-items: center; /* 置中 */
  gap: 24px;
  width: 100%;
}
.flight-card-block {
  width: 100%;
  max-width: 900px; /* 你想要的最大寬度 */
}
/* 動畫部分 */
.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}
.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}
.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateX(20px);
  opacity: 0;
}
.list-column {
  display: flex;
  flex-direction: column;
  align-items: center; /* 子項目垂直方向置中 */
  gap: 24px;
  width: 80%;
  margin: 0 auto;      /* 讓整個區塊在父容器水平置中 */
}
.v-container {
  padding-left: 0 !important;
  padding-right: 0 !important;
}
</style>
