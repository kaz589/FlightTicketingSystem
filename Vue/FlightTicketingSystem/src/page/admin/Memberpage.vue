<template>
  <div>
    <h1>會員管理</h1>
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
    <v-btn prepend-icon="mdi mdi-account-search" @click="searchOne">
      搜尋
    </v-btn>
  </v-container>

  <div>
    姓名 : {{ targetId.fullName }} <br />
    累積里程 : {{ targetId.totalMiles }} <br />
    剩餘里程 : {{ targetId.remainingMiles }}
  </div>
  <v-row>
    <v-col cols="6">
      <v-text-field
        v-model="cost"
        label="里程數"
        type="number"
        outlined
        style="width: 100%"
      />
    </v-col>
    <v-col cols="6">
      <v-btn prepend-icon="mdi mdi-account-search" @click="plusMiles">
        累積里程
      </v-btn>
      <v-btn prepend-icon="mdi mdi-account-search" @click="minusMiles">
        扣除里程
      </v-btn>
    </v-col>
  </v-row>

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
    <template v-slot:item.actions="{ item }">
      <div class="d-flex ga-2 justify-end">
        <!-- 調用edit函數 -->
        <v-icon
          color="medium-emphasis"
          icon="mdi-pencil"
          size="small"
          @click="edit(item)"
        ></v-icon>

        <!-- 調用remove函數 -->
        <v-icon
          color="medium-emphasis"
          icon="mdi-delete"
          size="small"
          @click="remove(item.memberId)"
        ></v-icon>
      </div>
    </template>
  </v-data-table>

  <hr />
  <br /><br />

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

  <br /><br />
  <hr />
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
    <v-btn prepend-icon="mdi mdi-account-remove" @click="deleteById">
      刪除會員
    </v-btn>
  </v-container>

  <v-container>
    <v-number-input
      v-model="updateId"
      :reverse="false"
      controlVariant="default"
      label="更新會員ID"
      :hideInput="false"
      :inset="false"
    >
    </v-number-input>

    <v-btn prepend-icon="mdi mdi-account-edit" @click="getUpdateData">
      更新會員
    </v-btn>
  </v-container>

  <!-- 彈出框html -->
  <v-dialog v-model="dialog" max-width="600">
    <v-card>
      <v-card-title>編輯會員 : {{ updateData.memberId }}號</v-card-title>
      <v-card-text>
        <v-form>
          <v-row>
            <v-col cols="12" md="4">
              <v-text-field
                v-model="updateData.fullName"
                label="全名"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="4">
              <v-text-field
                v-model="updateData.username"
                label="使用者名稱"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="4">
              <v-text-field
                v-model="updateData.password"
                label="使用者密碼"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="4">
              <v-text-field
                v-model="updateData.email"
                label="信箱"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="4">
              <v-text-field
                v-model="updateData.totalMiles"
                label="累積里程"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="4">
              <v-text-field
                v-model="updateData.remainingMiles"
                label="剩餘里程"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="4">
              <v-text-field
                v-model="updateData.phoneNumber"
                label="電話"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="4">
              <v-text-field
                v-model="updateData.registrationDate"
                label="註冊時間"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="4">
              <v-text-field
                v-model="updateData.emailVerified"
                label="信箱驗證"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="4">
              <v-text-field
                v-model="updateData.phoneVerified"
                label="電話驗證"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="4">
              <v-text-field
                v-model="updateData.membershipLevel"
                label="會員等級"
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
        <v-btn text color="primary" @click="update">保存</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, onMounted, shallowRef, watch } from "vue";
import { ApiMember } from "@/utils/API";
import Swal from "sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";

//初始運行函數(使之一開始就運行)
onMounted(() => {
  search();
});

// 設定空ref接查詢結果
const findMember = ref([]);
const allCities = ref(1);
//搜尋用id
const target = ref(1); //查詢單筆
const targetId = ref({
  memberId: "",
});
const targetAll = ref([]);

//搜尋單筆
function searchOne() {
  //判斷target是否大於0，大於0才查
  if (target.value > 0) {
    ApiMember.getMember(target.value).then((res) => {
      console.log(res.data);

      targetId.value = res.data;
    });
  } else {
    Swal.fire({
      icon: "error",
      title: "發生錯誤",
      text: "值不能小於0",
    });
  }
}
const cost = ref(0);

// 監聽 cost 的變化
watch(cost, (newValue) => {
  if (newValue < 0) {
    cost.value = 0; // 如果 cost 小於 0，重設為 0
    Swal.fire({
      icon: "error",
      title: "發生錯誤",
      text: "值不能小於0",
    });
  }
});

//累積里程(用async 和 await)
async function plusMiles() {
  console.log(target.value);
  console.log(cost.value);

  await ApiMember.increaseMiles(target.value, cost.value); //累積里程
  await ApiMember.getMember(target.value).then((res) => {
    //查詢目標
    targetId.value = res.data;
  });
  search(); //重新搜尋全部
}

//扣除里程(用.then())

function minusMiles() {
  console.log(target.value);
  console.log(cost.value);

  ApiMember.decreaseMiles(target.value, cost.value).then(() => {
    ApiMember.getMember(target.value).then((res) => {
      //查詢目標
      targetId.value = res.data;
    });
  });

  search(); //重新搜尋全部
}

//查詢全部的表頭
const headers = [
  { title: "會員ID", align: "start", value: "memberId" },
  { title: "全名", value: "fullName" },
  { title: "使用者名稱", value: "username" },
  { title: "電子郵件", value: "email" },
  { title: "電話", value: "phoneNumber" },
  { title: "累積里程", value: "totalMiles" },
  { title: "剩餘里程", value: "remainingMiles" },
  { title: "信箱驗證", value: "emailVerified" },
  { title: "電話驗證", value: "phoneVerified" },
  { title: "會員等級", value: "membershipLevel" },
  { title: "操作", value: "actions" },
];

//查詢全部函式
function search() {
  ApiMember.getAllMember().then((res) => {
    targetAll.value = res.data;
    // console.log(targetAll.value);
  });
}

//新增會員

const DEFAULT_FORM = ref({
  fullName: "1",
  username: "1",
  password: "11",
  email: "1",
  totalMiles: "1",
});

const insertData = ref({ ...DEFAULT_FORM.value });

function insert() {
  console.log("插入資料:", insertData.value); // 檢查傳入資料是否正確

  if (!insertData.value) {
    console.error("資料無效，請檢查 insertData");
    return; // 停止函式執行
  }

  ApiMember.insertMember(insertData.value)
    .then(() => {
      console.log("新增會員成功");
      search(); //重新搜尋全部
    })
    .catch((error) => {
      if (error.response) {
        // 請求已發送，並且後端回應了非2xx的狀態碼
        console.error("API 錯誤: ", error.response.data);
        console.error("錯誤狀態: ", error.response.status);
        console.error("錯誤頭部: ", error.response.headers);
      } else if (error.request) {
        // 請求已發送，但沒有收到回應
        console.error("沒有收到回應: ", error.request);
      } else {
        // 發生了設置請求時的錯誤
        console.error("請求設置錯誤: ", error.message);
      }
    });
}
//透過Id刪除資料
const deleteId = ref(0);

//全部表單後的刪除
function remove(id) {
  Swal.fire({
    title: "確定要刪除這個會員嗎?", // 提示標題
    showDenyButton: true, // 顯示拒絕按鈕
    // showCancelButton: true, // 顯示取消按鈕
    confirmButtonText: "刪除", // 確認按鈕的文字
    denyButtonText: `取消`, // 拒絕按鈕的文字
  }).then((result) => {
    /* 根據結果執行不同的操作 */
    if (result.isConfirmed) {
      // 如果選擇了確認

      ApiMember.deleteMemberById(id).then(() => {
        console.log("刪除ID : " + id);
        search();
      });

      Swal.fire("成功刪除", "", "success"); // 顯示成功的提示框
    } else if (result.isDenied) {
      // 如果選擇了拒絕
      Swal.fire("已取消", "", "info"); // 顯示信息框
    }
  });
}

function deleteById() {
  Swal.fire({
    title: "確定要刪除這個會員嗎?", // 提示標題
    showDenyButton: true, // 顯示拒絕按鈕
    // showCancelButton: true, // 顯示取消按鈕
    confirmButtonText: "刪除", // 確認按鈕的文字
    denyButtonText: `取消`, // 拒絕按鈕的文字
  }).then((result) => {
    /* 根據結果執行不同的操作 */
    if (result.isConfirmed) {
      // 如果選擇了確認，執行刪除
      ApiMember.deleteMemberById(deleteId.value).then(() => {
        console.log("刪除ID : " + deleteId.value);
        search();
      });

      Swal.fire("成功刪除", "", "success"); // 顯示成功的提示框
    } else if (result.isDenied) {
      // 如果選擇了拒絕
      Swal.fire("已取消", "", "info"); // 顯示信息框
    }
  });
}

// //修改資料

const DEFAULT_UPDATE = ref({
  memberId: "",
  fullName: "",
  username: "",
  password: "",
  email: "",
  totalMiles: "",
  remainingMiles: "",
  phoneNumber: "",
  registrationDate: "",
  emailVerified: "",
  phoneVerified: "",
  membershipLevel: "",
});

const updateData = ref({ ...DEFAULT_UPDATE.value });
const updateId = ref(1);
const dialog = shallowRef(false); //彈出對話框預設不彈出

function getUpdateData() {
  //查到要更改的資料，放入updateData內
  ApiMember.getMember(updateId.value).then((search_data) => {
    console.log(search_data.data.email);

    updateData.value = search_data.data;

    console.log(updateData.value.memberId); //找到更新的id
    dialog.value = true; //打開彈出框
  });
  // ApiMember.updateMember()
}

function update() {
  //透過api更改
  ApiMember.updateMember(updateData.value).then(() => {
    search(); //重新搜尋
    console.log("更新結束");
    Swal.fire("更新成功", "", "success"); // 顯示成功的提示框
    dialog.value = false; //關閉彈出框
  });
}

//已經有物件，直接更動
function edit(member) {
  console.log(member);
  updateData.value = member; //此處已經是proxy物件，不用再.value
  dialog.value = true; //打開彈出框
}
</script>

<style scoped></style>
