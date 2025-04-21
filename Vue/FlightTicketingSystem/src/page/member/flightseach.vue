<template>
  <!-- Title -->
  <div class="flex justify-center mb-8">
    <h1 class="text-4xl font-extrabold">易趣輕鬆飛</h1>
  </div>

  <!-- Search Bar -->
  <div class="flex flex-wrap justify-center items-center gap-4">
    <!-- From -->
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
        class="w-52"
        hide-details
        clearable
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
        class="w-52"
        hide-details
        clearable
      ></v-autocomplete>
    </v-col>

    <!-- Date range (one input, two pickers) -->
    <div
      class="flex items-center bg-gray-100 px-4 py-3 rounded-full text-sm w-64"
    >
      <FlatPickr
        v-model="range"
        :config="config"
        placeholder="Start → End"
        class="w-52 my-flatpickr"
      />
    </div>
    <!-- Search Button -->
    <button
      class="bg-green-400 hover:bg-green-500 text-white font-bold px-6 py-3 rounded-full"
      @click="console.log(range)"
    >
      Search
    </button>
  </div>
  <v-container>
    <v-row align="start" justify="center" cols="1" md="1">
      <v-col
        v-for="flight in flights"
        :key="flight.id"
        align-self="center"
        cols="7"
        md="7"
      >
        <FlightCard :flight="flight"></FlightCard>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { ApiAirport } from "@/utils/API";
import FlatPickr from "vue-flatpickr-component";
import "flatpickr/dist/flatpickr.css";
import FlightCard from "@/components/flight/flightcard.vue";
const range = ref([]);
const config = {
  mode: "range",
  dateFormat: "Y-m-d",
  time_24hr: true,
};

onMounted(() => {
  //getdisinairport();
});
const flights = ref([
  {
    id: 1,
    departureCity: "紐約",
    departureTime: "4/11 10:00 AM",
    arrivalCity: "倫敦",
    arrivalTime: "4/11 8:00 PM",
    flightid: 1,
    link: "#",
  },
  {
    id: 2,
    departureCity: "洛杉磯",
    departureTime: "4/12 11:00 AM",
    arrivalCity: "東京",
    arrivalTime: "4/13 3:00 PM",
    flightid: 1,
    link: "#",
  },
  {
    id: 3,
    departureCity: "巴黎",
    departureTime: "4/12 9:00 AM",
    arrivalCity: "柏林",
    arrivalTime: "4/12 11:30 AM",
    flightid: 1,
    link: "#",
  },
  {
    id: 4,
    departureCity: "香港",
    departureTime: "4/13 2:00 PM",
    arrivalCity: "悉尼",
    arrivalTime: "4/14 10:00 AM",
    flightid: 1,
    link: "#",
  },
  {
    id: 5,
    departureCity: "上海",
    departureTime: "4/14 6:00 PM",
    arrivalCity: "舊金山",
    arrivalTime: "4/14 2:00 PM",
    flightid: 1,
    link: "#",
  },
]);
function getdisinairport() {
  ApiAirport.DistinctAirportName().then((response) => {
    flights.value = response.data;
  });
}
</script>

<style scoped>
.my-flatpickr .flatpickr-input {
  border-radius: 0.75rem;
  box-shadow: 0 1px 4px rgb(0 0 0 / 6%);
  padding: 0.5rem 1rem;
  background: #7e2222;
  border: 1px solid #0f4392;
  font-size: 1rem;
  transition: border 0.2s, box-shadow 0.2s;
}
.my-flatpickr .flatpickr-input:focus {
  border-color: #4ade80;
  box-shadow: 0 0 0 2px #bbf7d0;
  outline: none;
}

/* 讓 placeholder 顏色和 Vuetify 一致 */
.my-flatpickr .flatpickr-input::placeholder {
  color: #990909; /* Tailwind gray-400 */
  opacity: 1;
}
</style>
