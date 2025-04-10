<template>
   <div>
    <h1>座位管理</h1>
  </div>
  <v-container>
    <v-btn prepend-icon="mdi mdi-magnify" @click="search"> 搜尋 </v-btn>
    <v-data-table
      :headers="headers"
      :items="items"
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
  { title: "座位編號", value: "seatNumber", sortable: true },
  { title: "座位類型", value: "seatclasname", sortable: true },
  { title: "票價", value: "price", sortable: true, align: "start"},
  { title: "航班ID", value: "flight.id", sortable: true , align: "start"},
  { title: "起始機場", value: "flight.originAirport.airportName", sortable: true ,align: "center" },
  { title: "抵達機場", value: "flight.destinationAirport.airportName", sortable: true ,align: "center"},
  { title: "起飛時間", value: "flight.arrivalTime", sortable: true ,align: "center"},
  { title: "預計抵達時間", value: "flight.departureTime", sortable: true ,align: "center"},
]);
//搜尋函式
function search() {
ApiSeats.getAllSeats()
    .then((res) => {
      console.log(res.data);
      
      items.value = res.data; // 更新表格數據
    })
    

}
const items = ref([]); // 表格內容數據
</script>

<style  scoped>

</style>