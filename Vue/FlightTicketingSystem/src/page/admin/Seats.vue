<template>
   <div>
    <h1>座位管理</h1>
  </div>
  <v-container>
    <v-btn prepend-icon="mdi mdi-magnify" @click="search"> 搜尋 </v-btn>
    <v-data-table
      :headers="headers"
      :items="items"
      :items-per-page="itemsPerPage"
      class="elevation-1"
    >
    </v-data-table>
  </v-container>
</template>

<script setup>
import { ref, watch, onMounted, shallowRef } from "vue";
import { ApiSeats, ApiAirport } from "@/utils/API";
// 表格標題資訊
const headers = ref([
  { title: "座位ID", value: "seatId", sortable: true, align: "start" }, //sortable: true 表示可排序
  { title: "航班ID", value: "flight.id", sortable: true },
  { title: "起始機場", value: "flight.originAirport.airportName", sortable: true },
  { title: "抵達機場", value: "flight.destinationAirport.airportName", sortable: true },
  
]);
//搜尋函式
function search() {
ApiSeats.getAllSeats()
    .then((res) => {
      items.value = res.data; // 更新表格數據
    })
    

}
const items = ref([]); // 表格內容數據
</script>

<style  scoped>

</style>