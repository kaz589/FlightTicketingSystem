<template>
  <v-container>
    <!-- 國家搜尋欄 -->
    <v-autocomplete
      clearable
      label="國家"
      :items="allCountries"
      v-model="searchFilters.countryRegion"
    ></v-autocomplete>
    <!-- 城市搜尋欄 -->
    <v-autocomplete
      clearable
      label="城市"
      :items="allCities"
       v-model="searchFilters.city"
    ></v-autocomplete>
   <!-- 關鍵字搜尋欄 -->
    <v-text-field label="Label" v-model="searchFilters.keyword"></v-text-field>
    
    <v-btn prepend-icon="mdi mdi-magnify" @click="search"> 搜尋 </v-btn>
    <v-btn color="primary" @click="openAddDialog">新增機場</v-btn>
    <br />
    <br />

    
    <v-data-table
      :headers="headers"
      :items="items"
      :items-per-page="itemsPerPage"
      :server-items-length="totalItems"
      hide-default-footer
      class="elevation-1"
    >
      <template v-slot:item.actions="{ item }">
        <div class="d-flex ga-2 justify-end">
          <v-icon
            color="medium-emphasis"
            icon="mdi-pencil"
            size="small"
            @click="edit(item.airportsId)"
          ></v-icon>

          <v-icon
            color="medium-emphasis"
            icon="mdi-delete"
            size="small"
            @click="remove(item.airportsId)"
          ></v-icon>
        </div>
      </template>
    </v-data-table>
   
   
   
    <!-- 分頁組件Pagination -->
    <v-pagination
      v-model="currentPage"
      :length="totalPages"
      @input="fetchData"
    ></v-pagination>

    <p>Current Page: {{ currentPage }}</p>
    <p>Total Pages: {{ totalPages }}</p>
    
    <!-- 新增修改用對話框 -->
    <v-dialog v-model="dialog" max-width="600">
      <v-card>
        <!-- 動態標題 -->
        <v-card-title>
          {{ isEditing ? "編輯機場" : "新增機場" }}
        </v-card-title>

        <v-card-text>
          <!-- 表單內容 -->
          <v-form ref="form">
            <v-row>
              <!-- 機場 ID -->
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="record.airportsId"
                  label="機場 ID"
                  :disabled="isEditing"
                   v-if="isEditing"
                  required
                ></v-text-field>
              </v-col>

              <!-- 機場 IATA 代碼 -->
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="record.iataCode"
                  label="IATA 代碼"
                  required
                ></v-text-field>
              </v-col>

              <!-- 機場名稱 -->
              <v-col cols="12">
                <v-text-field
                  v-model="record.airportName"
                  label="機場名稱"
                  required
                ></v-text-field>
              </v-col>

              <!-- 城市 -->
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="record.city"
                  label="城市"
                  required
                ></v-text-field>
              </v-col>

              <!-- 國家/地區 -->
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="record.countryRegion"
                  label="國家/地區"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
          </v-form>
        </v-card-text>

        <v-divider></v-divider>

        <!-- 操作按鈕 -->
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="dialog = false">取消</v-btn>
          <v-btn text color="primary" @click="save">保存</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- 確認刪除對話框 -->
    <v-dialog v-model="deleteDialog" max-width="400">
      <v-card>
        <v-card-title class="headline">確認刪除</v-card-title>
        <v-card-text>
          您確定要刪除該記錄嗎？此操作無法撤銷。
        </v-card-text>
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
import { ref, watch, onMounted,shallowRef  } from "vue";
import ApiAirport from "@/utils/Api";

//初始運行函式
onMounted(() => {
  fetchData();
  //search();
  getDistinctCities();
  getDistinctCountries();
});

/*

搜尋區塊

*/ 

//搜尋用初始數據
const searchFilters = ref({
  keyword: "",
  city: "",
  countryRegion: ""
});
const allCities = ref([]); //所有不重複城市數據
const allCountries = ref([]); //所有不重複國家數據
//取得所有不重複城市數據
const getDistinctCities = () => {
  
  ApiAirport.DistinctCities()
    .then((res) => {
     // console.log(res.data);
      allCities.value = res.data;
    })
    
};
//取得所有不重複國家數據
const getDistinctCountries = () => {
  ApiAirport.DistinctCountryRegions()
    .then((res) => {
    //  console.log(res.data);
      allCountries.value = res.data;
    })
    .catch((err) => {
      console.error(err);
    });
};
//搜尋函式
function search() {
  const { keyword, city, countryRegion } = searchFilters.value;

  // 確保參數有值，若為空則用默認值代替
  const searchKeyword = keyword || "all"; // 使用 "all" 表示不指定關鍵字
  const searchCity = city || "all"; // 使用 "all" 表示不指定城市
  const searchCountry = countryRegion || "all"; // 使用 "all" 表示不指定國家

  ApiAirport.searchAirports(searchKeyword, searchCity, searchCountry, currentPage.value, itemsPerPage.value)
    .then((res) => {
      items.value = res.data.content; // 更新表格數據
      totalItems.value = res.data.totalElements; // 總數據條數
      totalPages.value = res.data.totalPages; // 總頁數
    })
    .catch((err) => {
      console.error(err);
    });
}

/*

新增修改資料區塊

*/ 

// 新增修改對話框顯示狀態
const dialog = shallowRef(false);

// 是否處於編輯模式
const isEditing = shallowRef(false);
// 默認初始數據
const DEFAULT_RECORD = {
  airportsId: "",
  iataCode: "",
  airportName: "",
  city: "",
  countryRegion: "",
};

// 當前編輯或新增的記錄
const record = ref({ ...DEFAULT_RECORD });

// 重置記錄
function resetRecord() {
  record.value = { ...DEFAULT_RECORD };
}
//開啟新增對話框函式
function openAddDialog() {
  isEditing.value = false; // 設置為新增模式
  resetRecord(); // 重置表單
  dialog.value = true; // 打開對話框
}

// 保存方法
function save() {
  if ( !record.value.iataCode || !record.value.airportName || !record.value.city || !record.value.countryRegion) {
    alert("請填寫所有必填字段！");
    return;
  }

  if (isEditing.value) {
    console.log(record.value);
    
    // 更新機場數據
    ApiAirport.updateAirport(record.value)
      .then(() => {
        alert("更新成功！");
        dialog.value = false; // 關閉對話框
        fetchData(); // 刷新數據
      })
      .catch((err) => {
        console.error(err);
        alert("更新失敗！");
      });
  } else {
    console.log(record.value);
    // 新增機場數據
    ApiAirport.addAirport(record.value)
      .then(() => {
       
        dialog.value = false; // 關閉對話框
        fetchData(); // 刷新數據
      })
      .catch((err) => {
        console.error(err);
        alert("新增失敗！");
      });
  }
}

function edit(id) {
  isEditing.value = true

  const found = items.value.find(item => item.airportsId === id);

// 直接使用展開運算符賦值
record.value = { ...found }
  dialog.value = true
  console.log(id);
}
/*

表格資料區塊

*/ 
// 表格標題資訊
const headers = ref([
  { title: "機場 ID", value: "airportsId", align: "start" },
  { title: "機場IATA代碼", value: "iataCode" },
  { title: "機場名稱", value: "airportName" },
  { title: "城市", value: "city" },
  { title: "國家", value: "countryRegion" },
  { title: "操作", key: "actions", align: "end", sortable: false },
]);
const items = ref([]); // 表格內容數據
const totalItems = ref(0); // 總數據條數
const itemsPerPage = ref(5); // 每頁顯示數據條數












/*

刪除資料區塊

*/ 
function remove(id) {
  const found = items.value.find(item => item.airportsId === id);
  record.value = { ...found }; // 更新 record 為選中的記錄
  deleteDialog.value = true; // 打開刪除對話框
}
// 控制刪除對話框的顯示狀態
const deleteDialog = shallowRef(false); 
function deleteItem() {
  ApiAirport.deleteAirport(record.value.airportsId)
    .then(() => {
     // alert("刪除成功！");
      deleteDialog.value = false; // 關閉刪除對話框
      fetchData(); // 刷新數據
    })
    .catch((err) => {
      console.error(err);
      alert("刪除失敗！");
    });
}


/*

分頁組件區塊

*/ 

const currentPage = ref(1); // 當前頁數
const totalPages = ref(0); // 總頁數

// // 監聽當前頁數變化，自動加載新數據
watch(currentPage, (newPage, oldPage) => {
 // console.log(`Page changed from ${oldPage} to ${newPage}`);
 // fetchData(); // 確保這裡有調用數據加載函數
 search();
});


//Fetch data function
const fetchData = () => {
  ApiAirport.testPage(currentPage.value, itemsPerPage.value)
    .then((res) => {
     // console.log(res.data);
      items.value = res.data.content;
      totalItems.value = res.data.totalElements;
      totalPages.value = res.data.totalPages;
    })
    .catch((err) => {
      console.error(err);
    });
};
</script>

<style scoped></style>
