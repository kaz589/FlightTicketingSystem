<template>
    <div>
      <!-- 資料表 -->
      <v-data-table-server
        v-model:items-per-page="itemsPerPage"
        :headers="headers"
        :items="serverItems"
        :items-length="totalItems"
        :loading="loading"
        item-value="id"
        @update:options="loadItems"
      ></v-data-table-server>
  
      <!-- 分頁 -->
      <v-pagination
        v-model="currentPage"
        :length="totalPages"
        @input="onPageChange"
      ></v-pagination>
    </div>
  </template>
  
  <script>
  import { ref, computed, onMounted } from "vue";
  
  export default {
    setup() {
      // 響應式資料
      const itemsPerPage = ref(10); // 每頁顯示的筆數
      const headers = ref([
        { text: "ID", value: "id" },
        { text: "名稱", value: "name" },
        { text: "年齡", value: "age" },
      ]);
      const serverItems = ref([]); // 當前頁面的資料
      const totalItems = ref(0); // 資料總數
      const loading = ref(false); // 載入狀態
      const currentPage = ref(1); // 當前頁碼
  
      // 計算總頁數
      const totalPages = computed(() =>
        Math.ceil(totalItems.value / itemsPerPage.value)
      );
  
      // 當頁碼變化時觸發
      const onPageChange = (page) => {
        currentPage.value = page; // 更新頁碼
        loadItems(); // 請求新資料
      };
  
      // 請求伺服器資料
      const loadItems = () => {
        loading.value = true;
  
        // 模擬伺服器請求
        setTimeout(() => {
          // 假設伺服器根據當前頁碼和每頁筆數回傳資料
          const start = (currentPage.value - 1) * itemsPerPage.value;
          const end = start + itemsPerPage.value;
  
          // 模擬資料
          const allItems = [
            { id: 1, name: "John", age: 30 },
            { id: 2, name: "Jane", age: 25 },
            { id: 3, name: "Tom", age: 35 },
            { id: 4, name: "Lucy", age: 28 },
            { id: 5, name: "Mark", age: 40 },
            { id: 6, name: "Anna", age: 22 },
          ];
  
          // 更新資料
          serverItems.value = allItems.slice(start, end); // 當前頁面的資料
          totalItems.value = allItems.length; // 資料總數
          loading.value = false;
        }, 1000);
      };
  
      // 初始化資料
      onMounted(() => {
        loadItems();
      });
  
      return {
        itemsPerPage,
        headers,
        serverItems,
        totalItems,
        loading,
        currentPage,
        totalPages,
        onPageChange,
        loadItems,
      };
    },
  };
  </script>
  