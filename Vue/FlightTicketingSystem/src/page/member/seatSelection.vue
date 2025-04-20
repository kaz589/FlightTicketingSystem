<template>
  <h1>這是座位選擇</h1>
  <div class="legend">
    <div class="legend-item"><span class="seat first-class"></span> 頭等艙</div>
    <div class="legend-item"><span class="seat business-class"></span> 商務艙</div>
    <div class="legend-item"><span class="seat economy-class"></span> 經濟艙</div>
    <div class="legend-item"><span class="seat occupied"></span> 已預訂</div>
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
                'first-class': seat.seatClass === 'FIR',
                'business-class': seat.seatClass === 'BUS',
                'economy-class': seat.seatClass === 'ECO',
              },
            ]"
            :style="{ marginTop: seatIndex === aisleIndex - 1 ? aisleWidth : '0' }"
            @click="toggleSeat(columnIndex, seatIndex)"
          >
            {{ seat.seatNumber }}
          </div>
        </div>
      </div>
    </div>
  </v-container>
  <hr>
  <v-container   >  
    <v-row align="start" justify="center"  cols="1" md="1">
      <v-col
        v-for="selectseat in selectseats"
        :key="selectseat.id"
        align-self="center"
        cols="7"
        md="7"
      >
      <Seatscard :selectseat="selectseat"/>
      </v-col>
    </v-row>
  </v-container>
  <hr>
  <v-row justify="center">
    <v-col
       
        cols="4"
      >
      <p>總計： <span class="text-red-600 text-xl font-bold">${{totalPrice}}</span></p>
      <v-btn prepend-icon="$vuetify" stacked> Button</v-btn>
      </v-col>
    </v-row>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { ApiSeats } from "@/utils/API";
import Seatscard from "@/components/seats/Seatscard.vue";
import { useSeatStore } from '@/stores/useSeatStore';

onMounted(() => {
  const params = new URLSearchParams(window.location.search);
  flightId.value = params.get("flightid");
  getseat(flightId.value);
});

const flightId = ref(null);
const seats = ref([]);
 const seatColumns = ref([]);
let seatsPerColumn =0; // 每列座位數
const aisleIndex = ref(0); // 初始走道位置
const aisleWidth = '18px';
const seatStore = useSeatStore(); // 獲取 Pinia store
const selectseats = seatStore.selectseats; // 從 store 中獲取 selectseats
const totalPrice = ref(0);


const sections = ref([
  { id: "FIR", name: "頭等艙" },
  { id: "BUS", name: "商務艙" },
  { id: "ECO", name: "經濟艙" },
]);

const selectedSection = ref(sections.value[0].id);

//篩選艙等內的座位
const filteredSeatColumns = computed(() => {
  const filteredSeats = seats.value.filter(
    (seat) => seat.seatClass === selectedSection.value
  );
 // 同步 booked 狀態
 filteredSeats.forEach(seat => {
    seat.booked = seatStore.selectseats.some(
      selectedSeat => selectedSeat.seatNumber === seat.seatNumber
    );
  });
 console.log(filteredSeats);

 
  const columns = [];
  for (let i = 0; i < filteredSeats.length; i += seatsPerColumn) {
    columns.push(filteredSeats.slice(i, i + seatsPerColumn));
  }
  console.log(columns);
  
  return columns;
});


 function arrangeSeats() {
   seatColumns.value = filteredSeatColumns.value;
   filteredSeatColumns.value
   }

function toggleSeat(columnIndex, seatIndex) {

  const seat = filteredSeatColumns.value[columnIndex][seatIndex];
  seatStore.toggleSeat(seat);
  totalPrice.value=seatStore.totalPrice;
    
  
  
}

function getseat(id) {
  ApiSeats.getSeatsByFlightId(id).then((response) => {
    
    seats.value = response.data;
    const airplaneModel=seats.value[0].flight.airplaneModel;


     seatsPerColumn = airplaneModel.seatRowCount; // 每列座位數
     aisleIndex.value = airplaneModel.aisleStartPosition; // 初始走道位置
    
     
     
    arrangeSeats();
  });
}


</script>

<style scoped>
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
