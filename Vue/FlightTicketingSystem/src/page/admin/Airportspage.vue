<template>
  <v-container>
  <v-data-table
  :headers="headers"
  :items="items"
      :items-per-page="itemsPerPage"
      :server-items-length="totalItems"
      hide-default-footer
      class="elevation-1"
  ></v-data-table>
  <!-- Pagination -->
  <v-pagination
      v-model="currentPage"
      :length="totalPages"
      @input="fetchData"
    ></v-pagination>
    <p>Current Page: {{ currentPage }}</p>
    <p>Total Pages: {{ totalPages }}</p>
  </v-container>
</template>

<script setup>
import { ref ,watch} from "vue";
import { testapi } from "@/utils/Api";

const headers = ref([
  { title: "機場 ID", value: "airportsId", align: "start" },
  { title: "機場IATA代碼", value: "iataCode" },
  { title: "機場名稱", value: "airportName" },
  { title: "城市", value: "city" },
  { title: "國家", value: "countryRegion" },
]);
const items = ref([]); // 表格數據
const totalItems = ref(0); // 總數據條數
const itemsPerPage = ref(5); // 每頁顯示數據條數
const currentPage = ref(1); // 當前頁數
const totalPages = ref(0); // 總頁數
//Fetch data function
const fetchData = () => {
  console.log(currentPage.value, itemsPerPage.value);
  
  testapi(currentPage.value, itemsPerPage.value)
    .then((res) => {
      console.log(res.data);
      items.value = res.data.content; // 假設 API 返回的數據結構包含 "items" 和 "total"
      totalItems.value = res.data.totalElements;
      totalPages.value = res.data.totalPages; // 計算總頁數
    })
    .catch((err) => {
      console.error(err);
    });

};
// 初次加載數據
fetchData();

// 監聽當前頁數變化，自動加載數據
watch(currentPage, (newPage, oldPage) => {
  console.log(`Page changed from ${oldPage} to ${newPage}`);
  fetchData(); // 確保這裡有調用數據加載函數
});


</script>

<style scoped>

.custom-grid .v-data-table thead th,
.custom-grid .v-data-table tbody td {
  border: 1px solid black; /* 格線顏色 */
}
</style>
