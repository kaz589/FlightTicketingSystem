<template>
    <div>
     <h1>票務訂單管理</h1>
   </div>
   <v-container>
     <v-btn prepend-icon="mdi mdi-magnify" @click="search"> 搜尋 </v-btn>
     <v-data-table
       :headers="headers"
       :items="items"
       class="elevation-1"
     >
     <template v-slot:item.actions="{ item }">
        <div class="d-flex ga-2 justify-end">

          <v-icon
            color="medium-emphasis"
            icon="mdi-delete"
            size="small"
            @click="remove(item.ticketId)"
          ></v-icon>
        </div>
      </template>
     </v-data-table>


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
 </template>
 
 <script setup>
 import { ref, watch, onMounted, shallowRef } from "vue";
 import { ApiSeats, ApiAirport,ApiTicket } from "@/utils/API";
 const deleteDialog = shallowRef(false); // 控制刪除對話框的顯示狀態
 // 表格標題資訊
 const headers = ref([
   { title: "訂單ID", value: "ticketId", sortable: true, align: "start" }, //sortable: true 表示可排序
   { title: "購票人姓名", value: "memberName", sortable: true },
   { title: "訂票時間", value: "bookingTime", sortable: true },
   { title: "操作", key: "actions", align: "end", sortable: false },
 ]);
 const record = ref(); // 存儲當前選中的記錄

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
    ApiTicket.getAllTickets()
     .then((res) => {
        console.log(res.data);
        
       items.value = res.data; // 更新表格數據
     })
     
 
 }
 const items = ref([]); // 表格內容數據
 </script>
 
 <style  scoped>
 
 </style>