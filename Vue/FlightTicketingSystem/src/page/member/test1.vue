<template>
  <!-- Title -->
  <div class="flex justify-center mb-8">
  <h1 class="text-4xl font-extrabold">易趣輕鬆飛</h1>
</div>
 

  <!-- Search Bar -->
  <div class="flex flex-wrap justify-center items-center gap-4">
    <!-- From -->
    <div
      class="flex items-center bg-gray-100 px-4 py-3 rounded-full text-sm w-52"
    >
      <span class="mr-2 mdi mdi-airplane-takeoff"></span><strong>From:</strong>
      <input
        type="text"
        placeholder="Origin"
        class="bg-transparent outline-none ml-2 w-full"
      />
    </div>

    <!-- To -->
    <div
      class="flex items-center bg-gray-100 px-4 py-3 rounded-full text-sm w-52"
    >
      <span class="mr-2 mdi mdi-airplane-landing"></span><strong>To:</strong>
      <input
        type="text"
        placeholder="Destination"
        class="bg-transparent outline-none ml-2 w-full"
      />
    </div>

    <!-- Date range (one input, two pickers) -->
    <div
      class="flex items-center bg-gray-100 px-4 py-3 rounded-full text-sm w-64"
    >
      <span class="mr-2 mdi mdi-calendar-range"></span>
      <input
        type="text"
        ref="dateInput"
        placeholder="Start → End"
        class="bg-transparent outline-none w-full"
        readonly
      />
    </div>

   

    <!-- Search Button -->
    <button
      class="bg-green-400 hover:bg-green-500 text-white font-bold px-6 py-3 rounded-full"
    >
      Search
    </button>
  </div>
  <v-container>
    <v-row align="start" justify="center">
      <v-col
        v-for="flight in flights"
        :key="flight.id"
        align-self="center"
        cols="9"
        md="9"
      >
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
              @click="reveal = true"
            ></v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import flatpickr from "flatpickr";
import "flatpickr/dist/flatpickr.min.css";
const menuVisible = ref(false);

const flights = ref([
  {
    id: 1,
    departureCity: "紐約",
    departureTime: "4/11 10:00 AM",
    arrivalCity: "倫敦",
    arrivalTime: "4/11 8:00 PM",
    link: "#",
  },
  {
    id: 2,
    departureCity: "洛杉磯",
    departureTime: "4/12 11:00 AM",
    arrivalCity: "東京",
    arrivalTime: "4/13 3:00 PM",
    link: "#",
  },
  {
    id: 3,
    departureCity: "巴黎",
    departureTime: "4/12 9:00 AM",
    arrivalCity: "柏林",
    arrivalTime: "4/12 11:30 AM",
    link: "#",
  },
  {
    id: 4,
    departureCity: "香港",
    departureTime: "4/13 2:00 PM",
    arrivalCity: "悉尼",
    arrivalTime: "4/14 10:00 AM",
    link: "#",
  },
  {
    id: 5,
    departureCity: "上海",
    departureTime: "4/14 6:00 PM",
    arrivalCity: "舊金山",
    arrivalTime: "4/14 2:00 PM",
    link: "#",
  },
]);
</script>

<style scoped>
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
