<template>
    <h1>這是座位選擇</h1>
    <v-container class="d-flex justify-center">
      <div class="seat-map">
        <div v-for="(column, columnIndex) in seatColumns" :key="columnIndex" class="seat-column">
          <div
            v-for="(seat, seatIndex) in column"
            :key="seatIndex"
            :class="[
            'seat', 
            { 
              occupied: seat.occupied,
              'first-class': seat.type === 'first-class',
              'business-class': seat.type === 'business-class',
              'economy-class': seat.type === 'economy-class'
            }
          ]"
            @click="toggleSeat(columnIndex, seatIndex)"
          >
            {{ seat.label }}
          </div>
        </div>
      </div>
    </v-container>
  </template>
  
  <script setup>
  import { ref, onMounted } from "vue";
  import { ApiSeats } from "@/utils/API";
  
  onMounted(() => {
    const params = new URLSearchParams(window.location.search);
    flightId.value = params.get("flightid");
    getseat(flightId.value);
  });
  const seatss = ref([]);
  const flightId = ref(null);
  const seats = ref([
  { label: "A1", occupied: false, type: "first-class" },
  { label: "B1", occupied: true, type: "business-class" },
  { label: "C1", occupied: false, type: "economy-class" },
  { label: "A2", occupied: false, type: "first-class" },
  { label: "B2", occupied: false, type: "business-class" },
  { label: "C2", occupied: true, type: "economy-class" },
]);
  
  const seatColumns = ref([]);
  const seatsPerColumn = 3; // 每列座位數
  
  function arrangeSeats() {
    seatColumns.value = [];
    for (let i = 0; i < seats.value.length; i += seatsPerColumn) {
      seatColumns.value.push(seats.value.slice(i, i + seatsPerColumn));
    }
  }
  
  function toggleSeat(columnIndex, seatIndex) {
    const seat = seatColumns.value[columnIndex][seatIndex];
    if (!seat.occupied) {
      seat.occupied = !seat.occupied;
    }
    console.log(`Column: ${columnIndex}, Seat: ${seatIndex}, Label: ${seat.label}`);
  }
  
  function getseat(id) {
    ApiSeats.getSeatsByFlightId(id).then((response) => {
      console.log(response.data);
      seatss.value = response.data;
      arrangeSeats();
    });
  }
  
  // 初始化座位排列
  arrangeSeats();
  </script>
  
  <style scoped>
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
    width: 80px;
    height: 80px;
    background-color: #90caf9;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    border-radius: 5px;
  }
  
 
  /* 頭等艙 */
.seat.first-class {
  background-color: #ffd700; /* 金色 */
}

/* 商務艙 */
.seat.business-class {
  background-color: #8e44ad; /* 紫色 */
}

/* 經濟艙 */
.seat.economy-class {
  background-color: #3498db; /* 藍色 */
}
.seat.occupied {
    background-color: #e57373;
    cursor: not-allowed;
  }
  </style>
  