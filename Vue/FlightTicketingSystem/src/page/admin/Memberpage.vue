<template>
  <div>
    <h1>這是會員頁面</h1>
  </div>
  <v-container>
    <v-number-input
      v-model="target"
      :reverse="false"
      controlVariant="default"
      label="搜尋會員ID"
      :hideInput="false"
      :inset="false"
    >
    </v-number-input>
    <v-btn prepend-icon="mdi mdi-magnify" @click="searchOne"> 搜尋 </v-btn>
  </v-container>
  搜尋一個 : {{ targetId.fullName }}
  <br />
  <br />
  <hr />

  <v-btn prepend-icon="mdi mdi-magnify" @click="search"> 搜尋全部會員 </v-btn>

  <!-- <div>搜尋全部 : {{ targetAll }}</div> -->
  <!-- <hr /> -->
  <v-data-table
    v-if="targetAll.length > 0"
    :headers="headers"
    :items="targetAll"
    item-key="memberId"
  >
  </v-data-table>

  <!-- {{targetId.fullName}} -->
  <!-- 取得資料 -->
  <v-form>
    <v-row>
      <v-col
        ><v-text-field
          v-model="insertData.fullName"
          :counter="10"
          label="全名"
          required
        ></v-text-field
      ></v-col>
      <v-col
        ><v-text-field
          v-model="insertData.username"
          :counter="10"
          label="會員帳號"
          required
        ></v-text-field
      ></v-col>
      <v-col
        ><v-text-field
          v-model="insertData.password"
          :counter="10"
          label="會員密碼"
          required
        ></v-text-field
      ></v-col>
      <v-col
        ><v-text-field
          v-model="insertData.email"
          :counter="10"
          label="信箱"
          required
        ></v-text-field
      ></v-col>
      <v-col
        ><v-text-field
          v-model="insertData.totalMiles"
          :counter="10"
          label="累積里程"
          required
        ></v-text-field
      ></v-col>
    </v-row>
  </v-form>

  <v-btn prepend-icon="mdi mdi-account-plus" @click="insert"> 新增會員 </v-btn>

  <hr />
  <br />
  <br />
  <v-container>
    <v-number-input
      v-model="deleteId"
      :reverse="false"
      controlVariant="default"
      label="刪除會員ID"
      :hideInput="false"
      :inset="false"
    >
    </v-number-input>
    <v-btn prepend-icon="mdi mdi-magnify" @click="deleteById"> 刪除 </v-btn>
  </v-container>
</template>

<script setup>
import { ref,onMounted } from "vue";
import { ApiMember } from "@/utils/API";

//初始運行函數(使之一開始就運行)
onMounted(()=>{
  search();
})




// 設定空ref接查詢結果
const findMember = ref([]);
const allCities = ref(1);
//搜尋用id
const target = ref();
const targetId = ref({
  memberId: "",
});
const targetAll = ref([]);

//搜尋單筆
function searchOne() {
  ApiMember.getMember(target.value).then((res) => {
    targetId.value = res.data;
  });
}

//查詢全部的表頭
const headers = [
  { text: "會員ID", align: "start", keys: "memberId" },
  { text: "全名", value: "fullName" },
  { text: "電子郵件", value: "email" },
  { text: "電話", value: "phoneNumber" },
];

//查詢全部函式
function search() {
  ApiMember.getAllMember().then((res) => {
    targetAll.value = res.data;
    console.log(targetAll.value);
  });
}

//新增會員

const insertData = ref({
  fullName: "",
  username: "",
  password: "",
  email: "",
  totalMiles: "",
});

function insert() {
  console.log("插入資料:", insertData.value); // 檢查傳入資料是否正確

  if (!insertData.value) {
    console.error("資料無效，請檢查 insertData");
    return; // 停止函式執行
  }

  ApiMember.insertMember(insertData.value)
    .then(() => {
      console.log("新增會員");
    })
    .catch((error) => {
      console.error("新增會員時發生錯誤", error);
    });
}

const deleteId = ref(0);

function deleteById() {
  ApiMember.deleteMemberById(deleteId.value).then(() => {
    console.log("刪除ID : " + deleteId.value);
    search()
  });
}
</script>

<style scoped></style>
