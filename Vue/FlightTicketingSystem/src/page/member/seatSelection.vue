<template>
  <div>
  <div class="flight-header">
    <div class="airport-row">
      <div class="airport-col">
        <div v-if="flight" class="city-name">
          {{ flight?.originAirport?.city }}
        </div>
      </div>
      <div class="plane-icon">→</div>
      <div class="airport-col">
        <div v-if="flight" class="city-name">
          {{ flight?.destinationAirport?.city }}
        </div>
      </div>
    </div>
    <div class="flight-info">
      <div v-if="flight" class="date">
        {{ formatDepartureTime(flight.departureTime) }}
      </div>
    </div>
  </div>
  <br />
  <hr />
  <div class="legend">
    <div class="legend-item"><span class="seat first-class"></span> 頭等艙</div>

    <div class="legend-item">
      <span class="seat business-class"></span> 商務艙
    </div>
    <div class="legend-item">
      <span class="seat economy-class"></span> 經濟艙
    </div>
    <div class="legend-item"><span class="seat occupied"></span> 已預訂</div>
    <div class="legend-item"><span class="seat selected"></span> 已選擇</div>
  </div>
  <v-container class="d-flex justify-center">
    <div class="custom-width">
      <v-select
        v-model="selectedSection"
        @change="arrangeSeats"
        label="選擇艙等"
        :items="sections"
        item-title="name"
        item-value="id"
      ></v-select>
    </div>
  </v-container>
  <br />
  <v-container class="d-flex justify-center">
    <div class="seat-map-container">
      <div class="seat-map">
        <div
          v-for="(column, columnIndex) in filteredSeatColumns"
          :key="columnIndex"
          class="seat-column"
        >
          <div
            v-for="(seat, seatIndex) in column"
            :key="seatIndex"
            :class="[
              'seat',
              {
                occupied: seat.booked,

                selected: seat.selected,

                'first-class': seat.seatClass === 'FIR',
                'business-class': seat.seatClass === 'BUS',
                'economy-class': seat.seatClass === 'ECO',
              },
            ]"
            :style="{
              marginTop: seatIndex === aisleIndex - 1 ? aisleWidth : '0',
            }"
            @click="toggleSeat(columnIndex, seatIndex)"
          >
            {{ seat.seatNumber }}
          </div>
        </div>
      </div>
    </div>
  </v-container>

  <hr />
  <v-container>
    <v-row align="start" justify="center" cols="1" md="1">
      <v-col
        v-for="selectseat in selectseats"
        :key="selectseat.id"
        align-self="center"
        cols="7"
        md="7"
      >
        <Seatscard :selectseat="selectseat" :action-type="actionType" />
      </v-col>
    </v-row>
  </v-container>
  <hr />
  <v-row justify="center">
    <v-col cols="4" class="text-right">
      <p class="text-2xl">
        總計：
        <span class="text-red-600 text-3xl font-bold">
          ${{ seatStore.totalPrice }}
        </span>
      </p>
      <p class="text-2xl">
        預計累計里程：
        <span class="text-red-600 text-3xl font-bold">
          {{ seatStore.totalDistance }} KM
        </span>
      </p>
      <v-btn
        prepend-icon="mdi mdi-cash-sync"
        @click="$router.push('SeatPayment')"
        stacked
        size="large"
        class="text-xl py-4 px-8"
      >
        結帳</v-btn
      >
    </v-col>
  </v-row>
</div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { ApiSeats } from "@/utils/API";
import Seatscard from "@/components/seats/Seatscard.vue";

import { useSeatStore } from "@/stores/useSeatStore";
import { formatDepartureTime } from "@/utils/Date";

onMounted(() => {
  const params = new URLSearchParams(window.location.search);
  flightId.value = params.get("flightid");
  getseat(flightId.value);
});
const actionType = ref("cancel");
const flightId = ref(null);
const seats = ref([]);

const flight = ref([]);
let seatsPerColumn = 0; // 每列座位數
const aisleIndex = ref(0); // 初始走道位置
const aisleWidth = "18px";
const seatStore = useSeatStore(); // 獲取 Pinia store
const selectseats = seatStore.selectseats; // 從 store 中獲取 selectseats

const sections = ref([
  { id: "FIR", name: "頭等艙" },
  { id: "BUS", name: "商務艙" },
  { id: "ECO", name: "經濟艙" },
]);

const selectedSection = ref(sections.value[0].id);

//篩選艙等內的座位
const filteredSeatColumns = computed(() => {
  // 用 map 產生新物件，避免直接修改原 seats
  const filteredSeats = seats.value
    .filter((seat) => seat.seatClass === selectedSection.value)
    .map((seat) => ({
      ...seat,
      selected: seatStore.selectseats.some(
        (selectedSeat) => selectedSeat.seatNumber === seat.seatNumber
      ),
    }));

  const columns = [];
  for (let i = 0; i < filteredSeats.length; i += seatsPerColumn) {
    columns.push(filteredSeats.slice(i, i + seatsPerColumn));
  }

  return columns;
});

function arrangeSeats() {
  //seatColumns.value = filteredSeatColumns.value;
}

function toggleSeat(columnIndex, seatIndex) {
  const seat = filteredSeatColumns.value[columnIndex][seatIndex];
  console.log(seat);
  
  seatStore.toggleSeat(seat);
}

function getseat(id) {
  console.log(id);

  ApiSeats.getSeatsByFlightId(id).then((response) => {
    seats.value = response.data;

    const airplaneModel = seats.value[0].flight.airplaneModel;
    flight.value = seats.value[0].flight;
    seatsPerColumn = airplaneModel.seatRowCount; // 每列座位數
    aisleIndex.value = airplaneModel.aisleStartPosition; // 初始走道位置
    arrangeSeats();
  });
}
</script>

<style scoped>
.flight-header {
  margin-top: 20px;
  margin-bottom: 20px;
  text-align: center;
}
.airport-row {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 40px;
}
.airport-col {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.city-name {
  font-size: 2.5rem;
  font-weight: bold;
  letter-spacing: 2px;
}
.plane-icon {
  font-size: 2.2rem;
  color: black;
}
.flight-info {
  margin-top: 18px;
}
.label {
  font-size: 1rem;
  color: #888;
  margin-bottom: 4px;
}
.date {
  font-size: 1.15rem;
  font-weight: 600;
}
.seat-map-container {
  width: 100%;
  height: 350px; /* 你可以根據需要調整高度 */
  overflow: auto;
}
.seat-map {
  display: flex;
  flex-direction: row; /* 整個座位圖橫向排列 */
  justify-content: center;
  gap: 10px;
}

.seat-column {
  display: flex;
  flex-direction: column-reverse; /* 每列內的座位由下往上排列 */
  gap: 10px;
}

.seat {
  width: 50px;
  height: 50px;
  background-color: #90caf9;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  border-radius: 5px;
}

/* 頭等艙 */
.seat.first-class {
  background-color: #f0e68c; /* 更柔和的金色 */
}

/* 商務艙 */
.seat.business-class {
  background-color: #b39ddb; /* 柔和的淡紫色 */
}

/* 經濟艙 */
.seat.economy-class {
  background-color: #5dade2; /* 明亮的淺藍色 */
}

.seat.occupied {
  background-color: #e57373;
  cursor: not-allowed;
}

.controls {
  width: 40%;
  display: flex;
  justify-content: center; /* 置中元素 */

  margin: 20px 0;
  text-align: center;
}
.seat.selected {
  background: #43a047; /* 明亮的綠色 */
  color: #fff;
  border: 2px solid black; /* 可選，加強辨識度 */
}

.legend {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  gap: 20px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 5px;
}
.custom-width {
  width: 150px; /* 固定寬度 */
}
</style>