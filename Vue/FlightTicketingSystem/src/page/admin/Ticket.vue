<template>
  <div>
    <h1 class="text-4xl font-extrabold text-center mb-4">票務訂單管理</h1>
  </div>
  <v-container>
    <v-container fluid class="py-8">
      <v-row justify="center">
        <v-col cols="12" md="8" lg="6">
          <!-- 第一行 -->
          <v-row dense class="mb-2">
            <v-col cols="12" md="6">
              <v-autocomplete
                clearable
                label="起始機場"
                :items="allairports"
                v-model="searchFilters.originAirport"
              />
            </v-col>
            <v-col cols="12" md="6">
              <v-autocomplete
                clearable
                label="抵達機場"
                :items="allairports"
                v-model="searchFilters.destinationAirport"
              />
            </v-col>
          </v-row>
          <!-- 第二行 -->
          <v-row dense class="mb-2">
            <v-col cols="12" md="6">
              <VueDatePicker
                id="arrivalTime"
                v-model="searchFilters.arrivalTime"
                :format="'yyyy/MM/dd--HH:mm'"
                :enable-time="true"
                placeholder="起飛日期"
              />
            </v-col>
            <v-col cols="12" md="6">
              <VueDatePicker
                id="departureTime"
                v-model="searchFilters.departureTime"
                :format="'yyyy/MM/dd--HH:mm'"
                :enable-time="true"
                placeholder="降落日期"
              />
            </v-col>
          </v-row>
          <!-- 第三行 -->
          <v-row dense class="mb-2">
            <v-col cols="12" md="6">
              <v-select
                label="付款狀態"
                :items="paidOptions"
                v-model="searchFilters.paid"
                item-title="text"
                item-value="value"
              />
            </v-col>
            <v-col cols="12" md="6">
              <v-text-field
                label="關鍵字(訂單ID)"
                v-model="searchFilters.keyword"
              />
            </v-col>
          </v-row>
          <!-- 第四行 -->
          <v-row>
            <v-col cols="12" class="text-right">
              <v-btn prepend-icon="mdi-magnify" @click="search" color="primary">
                搜尋
              </v-btn>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
    <v-data-table
      :headers="headers"
      :items="items"
      class="elevation-1 my-bordered-table"
      @update:sort-by="updateSortBy"
    >
    <template v-slot:item.paid="{ item }">
  <v-chip :style="{ background: item.paid ? '#388e3c' : '#d32f2f', color: '#fff' }" class="status-chip">
    {{ item.paid ? "已付款" : "未付款" }}
  </v-chip>
</template>
      <template v-slot:item.actions="{ item }">
        <div class="d-flex ga-2 justify-end">
          <v-icon
            color="primary"
            icon="mdi-eye"
            size="small"
            class="mr-2"
            @click="showDialog(item)"
          ></v-icon>
          <v-icon
            color="medium-emphasis"
            icon="mdi-delete"
            size="small"
            @click="remove(item.ticketId)"
          ></v-icon>
        </div>
      </template>
    </v-data-table>
    <!-- 分頁組件Pagination -->
    <v-pagination v-model="currentPage" :length="totalPages"></v-pagination>
    <!-- 確認刪除對話框 -->
    <v-dialog v-model="deleteDialog" max-width="400">
      <v-card>
        <v-card-title class="headline">確認刪除</v-card-title>
        <v-card-text> 您確定要刪除該記錄嗎？此操作無法撤銷。 </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="deleteDialog = false">取消</v-btn>
          <v-btn text color="red" @click="deleteItem">確定</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
  <OrderDetailDialog
    v-model="dialogVisible"
    :order="selectedOrder"
    :action-type="'delete'"
  />
</template>

<script setup>
import { ref, watch, onMounted, shallowRef } from "vue";
import { ApiSeats, ApiAirport, ApiTicket } from "@/utils/API";
import OrderDetailDialog from "@/components/ticket/OrderDetailDialog.vue";
const deleteDialog = shallowRef(false); // 控制刪除對話框的顯示狀態
import VueDatePicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
import { showLoadingSwal, closeLoadingSwal,showSuccessSwal } from '@/utils/swalLoading'

onMounted(() => {
  getDistinctAirportName();
});
// 表格標題資訊
const headers = ref([
  { title: "訂單ID", value: "ticketId", sortable: true, align: "start" }, //sortable: true 表示可排序
  { title: "購票人帳號", value: "memberName", sortable: true },
  { title: "訂票時間", value: "bookingTime", sortable: true },
  { title: "航班編號", value: "flightid", sortable: true },
  { title: "出發機場", value: "originAirport", sortable: false },
  { title: "到達機場", value: "destinationAirport", sortable: false },
  { title: "出發時間", value: "arrivalDate", sortable: true },
  { title: "到達時間", value: "departureDate", sortable: false },
  { title: "總金額", value: "totalAmount", sortable: true },
  { title: "總累計里程", value: "totalDistance", sortable: true },
  { title: "付款狀態", value: "paid", sortable: true },
  { title: "操作", key: "actions", align: "end", sortable: false },
]);
//搜尋用初始數據
const searchFilters = ref({
  originAirport: "",
  destinationAirport: "",
  arrivalTime: "",
  departureTime: "",
  paid: "",
  keyword: "",
});
const paidOptions = [
  { text: "全部", value: "" },
  { text: "已付款", value: true },
  { text: "未付款", value: false },
];
const record = ref(); // 存儲當前選中的記錄

const allairports = ref([]); //所有不重複機場數據
const getDistinctAirportName = () => {
  ApiAirport.DistinctAirportName().then((res) => {
    allairports.value = res.data;
  });
};

// 重置記錄
function resetRecord() {
  record.value = { ...DEFAULT_RECORD };
}

function remove(id) {
  const found = items.value.find((item) => item.ticketId === id);
  if (!found) {
    alert("未找到該記錄！");
    return;
  }

  // 更新 record 為選中的記錄，並顯示刪除確認對話框
  record.value = { ...found };
  deleteDialog.value = true; // 顯示刪除對話框
}
function deleteItem() {
  if (!record.value) {
    alert("未選中刪除的記錄！");
    return;
  }

  ApiTicket.deleteTicket(record.value.ticketId)
    .then(() => {
      alert("刪除成功！");
      deleteDialog.value = false; // 關閉刪除對話框
      search(); // 刷新數據
    })
    .catch((err) => {
      alert("刪除失敗！");
    });
}
//搜尋函式
function search() {
  showLoadingSwal('請稍候', '資料送出中...')
  

  ApiTicket.searchTickets(
    searchFilters.value.originAirport,
    searchFilters.value.destinationAirport,
    formatDate(searchFilters.value.arrivalTime),
    formatDate(searchFilters.value.departureTime),
    searchFilters.value.paid === "" ? undefined : searchFilters.value.paid,
    searchFilters.value.keyword,
    currentPage.value,
    10, // 或 size.value
    searchFilters.value.sortBy,
    searchFilters.value.sortOrder
  ).then((res) => {
    items.value = res.data.content; // 假設後端回傳 Page 格式有 content
    totalPages.value = res.data.totalPages || 1;
    totalItems.value = res.data.totalElements; // 總數據條數
      totalPages.value = res.data.totalPages; // 總頁數
  }).finally(() => {
    closeLoadingSwal();
    showSuccessSwal();
  });
}
const dialogVisible = ref(false);
const selectedOrder = ref(null);
function showDialog(order) {
  selectedOrder.value = order;
  dialogVisible.value = true;
}
const totalItems=ref(0);
const items = ref([]); // 表格內容數據
const currentPage = ref(1); // 當前頁數
const totalPages = ref(1); // 總頁數

function updateSortBy(sortBy) {
  // 確保 `sortBy` 有值
  if (!sortBy) {
    console.error("sortBy is undefined");
    return;
  }
  searchFilters.value.sortBy = sortBy[0].key || "airportsId";
  searchFilters.value.sortOrder = sortBy[0].order || "asc";
}
watch(currentPage, () => {
  search();
});

// 格式化日期（如果需要）
const formatDate = (date) => {
    if (!date) return undefined;
    // 假設 date 是 Date 物件
    const pad = (n) => (n < 10 ? "0" + n : n);
    const yyyy = date.getFullYear();
    const MM = pad(date.getMonth() + 1);
    const dd = pad(date.getDate());
    const HH = pad(date.getHours());
    const mm = pad(date.getMinutes());
    const ss = pad(date.getSeconds());
    return `${yyyy}-${MM}-${dd} ${HH}:${mm}:${ss}`;
  };
</script>

<style scoped>
.status-chip {
  margin-left: 0;
  font-size: 0.95rem;
}
</style>
