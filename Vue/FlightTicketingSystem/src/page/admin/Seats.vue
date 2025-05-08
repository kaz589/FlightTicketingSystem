<template>
  <div>
    <h1>座位管理</h1>
  </div>
  <v-container>
    <v-row align="center" class="mb-4">
      <v-col cols="12" md="3">
        <v-text-field
          v-model="searchFlightId"
          label="航班ID"
          type="number"
          dense
          clearable
        ></v-text-field>
      </v-col>
      <v-col cols="12" md="3">
        <v-select
          v-model="searchStatus"
          :items="statusOptions"
          item-title="label"
          item-value="value"
          label="狀態"
          dense
          clearable
        ></v-select>
      </v-col>
      <v-col cols="12" md="2">
        <v-btn prepend-icon="mdi-magnify" @click="search">搜尋</v-btn>
      </v-col>
    </v-row>
    <v-data-table :headers="headers" :items="items" class="elevation-1">
      <template v-slot:item.booked="{ item }">
        <v-chip
          :style="{
            background: item.booked ? '#388e3c' : '#d32f2f',
            color: '#fff',
          }"
          class="status-chip"
        >
          {{ item.booked ? "已預定" : "未預定" }}
        </v-chip>
      </template>
    </v-data-table>
  </v-container>
</template>

<script setup>
import { ref, watch, onMounted, shallowRef } from "vue";
import { ApiSeats, ApiAirport } from "@/utils/API";

onMounted(() => {
  search();
})
// === 搜尋條件的資料 ===
const searchFlightId = ref("");
const searchStatus = ref("all");
const statusOptions = [
  { label: "全部", value: "all" },
  { label: "已預定", value: "true" },
  { label: "未預定", value: "false" },
];

// 表格標題資訊
const headers = ref([
  { title: "座位ID", value: "seatId", sortable: true, align: "start" }, //sortable: true 表示可排序
  { title: "座位編號", value: "seatNumber", sortable: true },
  { title: "座位類型", value: "seatclasname", sortable: true },
  { title: "票價", value: "price", sortable: true, align: "start" },
  { title: "是否已預定", value: "booked", sortable: true, align: "start" },
  {
    title: "預定顧客帳號",
    value: "ticket.member.username",
    sortable: true,
    align: "start",
  },
  { title: "航班ID", value: "flight.id", sortable: true, align: "start" },
  {
    title: "起始機場",
    value: "flight.originAirport.airportName",
    sortable: true,
    align: "center",
  },
  {
    title: "抵達機場",
    value: "flight.destinationAirport.airportName",
    sortable: true,
    align: "center",
  },
  {
    title: "起飛時間",
    value: "flight.arrivalTime",
    sortable: true,
    align: "center",
  },
  {
    title: "預計抵達時間",
    value: "flight.departureTime",
    sortable: true,
    align: "center",
  },
]);


// === 搜尋函式 ===
async function search() {
  // 取得航班ID，空值或非數字視為 0
  let flightId = searchFlightId.value;
  if (!flightId || isNaN(flightId)) flightId = 0;

  // 狀態轉換
   let status = searchStatus.value || "all";
  try {
    console.log(status);
    
    const res = await ApiSeats.getSeatsByFlightIdAndStatus(flightId, status);
    items.value = res.data;
  } catch (e) {

    items.value = [];
    alert("查詢失敗或查無資料");
  }
}
const items = ref([]); // 表格內容數據
</script>

<style scoped></style>
