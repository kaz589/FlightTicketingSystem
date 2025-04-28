<template>
  <v-row>
    
    <v-col cols="12" md="3">
      <v-card class="mx-auto pa-2 no-shadow">
        <v-list>
          <h2>您好! 歡迎回來</h2>
          <br />

          <!-- click變換pageControl的值  -->
          <v-list-item
            v-for="(item, i) in items"
            :key="i"
            :value="item"
            color="primary"
            rounded="xl"
            @click="pageControl = i + 1"
          >
            <template v-slot:prepend>
              <v-icon :icon="item.icon"></v-icon>
            </template>

            <v-list-item-title v-text="item.text"></v-list-item-title>
          </v-list-item>
        </v-list>
      </v-card>
    </v-col>

    <!-- 右侧区域 -->
    <v-col cols="12" md="3" v-if="pageControl === 1">
      <v-card class="mx-auto" max-width="344" style="">
        <v-card-text
          style="
            border: 0.5px solid #003366; /* 設置邊框顏色和寬度 */
            padding: 24px; /* 增加內邊距，讓內容離邊框更遠 */
            border-radius: 12px; /* 圓角效果 */
          "
        >
          <!-- <br />
          <p class="text-h4 font-weight-black"></p>
          <br />
          <br /> -->
          <div>
            <h2>個人訊息</h2>
          </div>
          <hr />

          <div class="text-medium-emphasis">
            <section>
              <div>
                <div class="title">電子郵件</div>
                <span class="contentIn">{{ searchUser.email }}</span>
              </div>
            </section>
            <br />
            <hr />
            <section>
              <div>
                <div class="title">剩餘里程</div>
                <span class="contentIn">{{
                  searchUser.remainingMiles === 0
                    ? "尚未累積里程"
                    : searchUser.remainingMiles
                }}</span>
              </div>
            </section>
            <br />
            <hr />
            <section>
              <div>
                <div class="title">會員等級</div>
                <span class="contentIn">{{ searchUser.membershipLevel }}</span>
              </div>
            </section>
          </div>
          <br />
          <v-card-actions class="d-flex justify-end">
            <v-btn
              color="#003366"
              text="詳細資訊"
              variant="text"
              @click="show"
            ></v-btn>
          </v-card-actions>
        </v-card-text>

        <v-expand-transition>
          <v-card
            v-if="reveal"
            class="position-absolute w-100 pa-4"
            height="100%"
            style="
              bottom: 0;
              border: 0.5px solid #003366; /* 設置邊框顏色和寬度 */
              padding: 24px; /* 增加內邊距，讓內容離邊框更遠 */
              border-radius: 12px; /* 圓角效果 */
            "
          >
            <v-row align="center">
              <v-avatar size="64" class="mr-4">
                <img
                  :src="getPictureUrl(searchUser.picture)"
                  alt="圖片未顯示"
                />
              </v-avatar>

              <div>
                <h3 class="mb-1">{{ searchUser.fullName }}</h3>
              </div>
            </v-row>

            <v-divider class="my-4"></v-divider>

            <v-row>
              <v-col cols="6">
                <div class="text-subtitle-1 font-weight-medium">剩餘里程</div>
                <div class="text-h6 text-primary">
                  {{ searchUser.remainingMiles }} 公里
                </div>
              </v-col>
              <v-col cols="6">
                <div class="text-subtitle-1 font-weight-medium">累積里程</div>
                <div class="text-h6 text-success">
                  {{ searchUser.totalMiles }} 公里
                </div>
              </v-col>
            </v-row>
            <v-row justify="end">
              <v-btn>里程兌換</v-btn>
            </v-row>

            <v-divider class="my-4"></v-divider>

            <!-- 現在訂購區塊 -->
            <div class="mb-4">
              <div class="text-subtitle-1 font-weight-medium mb-2">
                現在馬上訂購機票
              </div>
            </div>

            <v-card-actions class="pt-0">
              <v-row>
                <v-btn>前往</v-btn>
              </v-row>

              <v-btn
                color="#003366"
                text="關閉資訊"
                variant="text"
                @click="hide"
              ></v-btn>
            </v-card-actions>
          </v-card>
        </v-expand-transition>
      </v-card>
    </v-col>
    <!-- 可以在這邊製作區塊2的網頁內容 -->
    <v-col cols="12" md="6" v-if="pageControl === 2">
      <v-card class="mx-auto pa-2 no-shadow">

        
        
        

            

              <TicketOrderList/>
          

          
      
      </v-card>
    </v-col>
    <!-- 區塊2結束 -->

    <!-- 可以在這邊製作區塊3的網頁內容 -->
    <v-col cols="12" md="6" v-if="pageControl === 3">
      <v-card class="mx-auto pa-2 no-shadow">
        <v-list>
          <h2>另一個區域</h2>
          <v-list-item>
            <v-list-item-title>這是右側區域3的內容</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-card>
    </v-col>
    <!-- 區塊2結束 -->

    <!-- 詳細資料 第一個區塊 -->
    <v-card-text
      v-if="detailControl && pageControl === 1"
      style="
        border: 0.5px solid #003366; /* 設置邊框顏色和寬度 */
        padding: 24px; /* 增加內邊距，讓內容離邊框更遠 */
        border-radius: 12px; /* 圓角效果 */
      "
    >
      <!-- <br />
          <p class="text-h4 font-weight-black"></p>
          <br />
          <br /> -->
      <div>
        <h2>詳細資訊</h2>
      </div>
      <hr class="custom-hr" />
      <br /><br />

      <div class="text-medium-emphasis">
        <section>
          <v-form v-model="formValid">
            <v-row>
              <v-col cols="5">
                <v-text-field
                  :readonly="loading"
                  class="mb-2 remove-focus-bg"
                  label="會員姓名"
                  clearable
                  :rules="[rules.required]"
                  v-model="searchUser.fullName"
                ></v-text-field>
              </v-col>
              <v-col cols="7">
                <v-text-field
                  class="mb-2"
                  label="帳號"
                  clearable
                  v-model="searchUser.username"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="5">
                <v-text-field
                  class="mb-2"
                  label="電話"
                  clearable
                  :rules="[rules.phone]"
                  v-model="searchUser.phoneNumber"
                ></v-text-field>
              </v-col>
              <v-col cols="7">
                <v-text-field
                  class="mb-2"
                  label="電子郵件"
                  clearable
                  :rules="[rules.email]"
                  v-model="searchUser.email"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12">
                <v-file-input
                  v-model="file"
                  label="選擇大頭貼"
                  accept="image/*"
                  @change="onFileChange"
                ></v-file-input>
              </v-col>
            </v-row>
            <br />

            <!-- :disabled="!formValid"  -->
            <v-btn
              color="black"
              size="large"
              type="button"
              variant="elevated"
              @click="handleUpdate"
              :disabled="!formValid"
            >
              更新
            </v-btn>
          </v-form>
        </section>
      </div>
    </v-card-text>
  </v-row>

  <!-- 控制 dialog 顯示 -->
  <v-dialog v-model="dialog" max-width="400" persistent>
    <!-- 編輯卡片 -->
    <v-confirm-edit
      v-model="model"
      disabled="true"
      @save="handleSave"
      @cancel="dialog = false"
    >
      <template v-slot:default="{ model: proxyModel, actions }">
        <v-card>
          <template v-slot:title>
            <div class="d-flex justify-space-between align-center">
              <span>修改{{ editLabel }}</span>

              <!-- 加入關閉按鈕 -->
              <v-btn icon @click="dialog = false" elevation="0">
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </div>
          </template>

          <template v-slot:text>
            <v-text-field
              v-model="proxyModel.value"
              label="更改為"
              :messages="`修改內容: ${editValue}`"
            ></v-text-field>
          </template>

          <template v-slot:actions>
            <v-spacer></v-spacer>
            <component :is="actions" />
          </template>
        </v-card>
      </template>
    </v-confirm-edit>
  </v-dialog>
</template>

<script setup>
import { ref, onMounted, watch, computed } from "vue";
import { useAuthStore } from "@/stores/auth"; // 引入 Pinia store
import { ApiMember } from "@/utils/API";
import Swal from "sweetalert2";
import TicketOrderList from "@/page/member/TicketOrderList.vue";

//#region  第一區塊(勿更動)
// Pinia store 實例
const authStore = useAuthStore();

// 設定控制按鈕 (1為個人資訊, 2為預訂紀錄, 3為價格通知)
const pageControl = ref(1);
// 控制詳細資料
let detailControl = ref(false);
let reveal = ref(false);

const items = [
  { text: "個人資訊", icon: "mdi-account" },
  { text: "你的預訂紀錄", icon: "mdi-clock" },
  { text: "價格通知", icon: "mdi mdi-bell-outline" },
];

const a = ref();
const searchUser = ref({}); // 搜尋使用者資料
const searchUserDef = ref({
  email: "",
  totalMiles: 1234,
  membershipLevel: "silver",
});

// 監控 searchUser 的變化，並在資料加載完成後更新 searchUserDef
watch(searchUser, (newVal) => {
  if (newVal.email) {
    searchUserDef.value = {
      email: newVal.email,
      totalMiles: newVal.totalMiles || 0,
      membershipLevel: newVal.membershipLevel || "silver",
    };
  }
});

onMounted(() => {
  //預設資料為空
  model.value = "";
  enter();
});

function enter() {
  // 先抓取使用者資料
  a.value = authStore.user.memberId;

  // 如果是用戶名，根據用戶名查找會員
  ApiMember.getMember(a.value)
    .then((res) => {
      if (res.status === 200) {
        searchUser.value = res.data;
        console.log(searchUser.value); // 確認是否正確返回資料
      } else {
        console.error("API 返回錯誤狀態碼:", res.status);
      }
    })
    .catch((error) => {
      console.error("API 請求錯誤:", error);
    });
}

// 預設欄位資料
// const fields = computed(() => [
//   { label: "會員姓名", value: searchUser.value.fullName || "尚未填寫" },
//   { label: "帳號", value: searchUser.value.username || "尚未填寫" },
//   { label: "電子郵件", value: searchUser.value.email || "尚未填寫" },
//   { label: "電話", value: searchUser.value.phoneNumber || "尚未填寫" },
// ]);

//彈出框

const dialog = ref(false);
const model = ref("原始資料");
// const editLabel = ref();
// const editValue = ref();
// const defaultEditValue = ref();
// const proxyModel = ref({
//   value: "",
// }); // proxyModel預設值

// // 這裡是監控 `proxyModel` 的變化，並且可以根據需求修改
// watch(proxyModel.value, (newVal) => {
//   console.log("proxyModel 更新:", newVal);
// });

// function editField(field, index) {
//   //開啟編輯視窗
//   openEdit();

//   proxyModel.value = { value: editValue.value }; // 更新 proxyModel 的內容
//   console.log("index", index);

//   // 編輯欄位的邏輯
//   // console.log("編輯欄位:", field.value);
//   //抓取field當中，第field個(field.label可以抓到編輯欄位!!!!!!!!!)
//   editLabel.value = field.label;
//   editValue.value = field.value;
//   //抓取field當中，第field個(field.value可以抓到值!!!!!!!!!)
// }

function show() {
  reveal.value = true;
  detailControl.value = true;
}

function hide() {
  reveal.value = false;
  detailControl.value = false;
}

//區分第三方登入和一般登入的圖片取得
function getPictureUrl(pic) {
  if (!pic) return "/images/default1.png";
  if (pic.startsWith("http")) return pic;
  return "http://localhost:8080" + pic;
}

// // 開啟編輯視窗
// function openEdit() {
//   dialog.value = true;
// }

// function handleSave() {
//   // 這裡可以抓到 model 的值()
//   console.log("保存的值:", model);
//   //先找到更改的資訊
//   //把model.value指定給searchUser.value

//   console.log("更改的項目:", editLabel.value);
//   if (editLabel.value === "會員姓名") {
//     searchUser.value.fullName = model.value;
//   } else if (editLabel.value === "帳號") {
//     searchUser.value.username = model.value;
//   } else if (editLabel.value === "電子郵件") {
//     searchUser.value.email = model.value;
//   } else if (editLabel.value === "電話") {
//     searchUser.value.phoneNumber = model.value;
//   }
//   // searchUser.value.fullName = model.value;
//   console.log("更改的值:", editValue.value);

//   ApiMember.updateMember(searchUser.value).then((res) => {
//     if (res.status === 200) {
//       console.log("更新成功");
//       //放sweetalert成功
//       Swal.fire({
//         title: "更新成功!",
//         icon: "success",
//         draggable: true,
//       });

//       // 清空 model
//       model.value = "";
//       // 關閉對話框
//       dialog.value = false;
//     }
//   });
// }

//詳細資訊 (更新版)
const formValid = ref(false);
const required = (value) => !!value || "此欄位為必填";
const email = (value) =>
  /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value) || "請輸入有效的電子郵件";
const phone = (value) => /^09\d{8}$/.test(value) || "請輸入正確的手機號碼";

const rules = {
  required,
  email,
  phone,
};

//新增更改資訊
function handleUpdate() {
  console.log("開始更新");

  console.log("file url: ", searchUser.value);
  console.log(searchUser.value);
  console.log(
    "送出前的 searchUser 資料：",
    JSON.stringify(searchUser.value, null, 2)
  );
  ApiMember.updateMember(searchUser.value).then((res) => {
    if (res.status === 200) {
      console.log(res.data); // 確保這裡的 picture URL 正確
      console.log("更新成功");
      //放sweetalert成功
      Swal.fire({
        title: "更新成功!",
        icon: "success",
        draggable: true,
      });
      //更新大頭照
      authStore.user.picture = res.data.picture;
    }
  });
}

const file = ref(null);

//更新照片
function onFileChange(event) {
  let file;

  if (event.target && event.target.files) {
    // 標準 HTML input type="file" 的情況
    file = event.target.files[0];
  } else if (Array.isArray(event) && event[0] instanceof File) {
    // 有些元件可能直接傳 File 陣列
    file = event[0];
  } else if (event && event[0] && event[0].file instanceof File) {
    // MDB 的元件可能是這樣的格式
    file = event[0].file;
  } else {
    console.error("無法解析檔案格式", event);
    return;
  }

  console.log("實際上傳的檔案是：", file);
  console.log("file instanceof File: ", file instanceof File);

  const formData = new FormData();
  formData.append("file", file);

  ApiMember.pictureUpload(formData)
    .then((res) => {
      const imageUrl = res.data.imageUrl; // 回傳的圖片 URL
      searchUser.value.picture = imageUrl;
    })
    .catch((err) => {
      console.error("上傳失敗", err);
    });
}

//#endregion
</script>

<style scoped>
.no-shadow {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); /* 添加陰影 */
  border-radius: 12px; /* 圓角效果 */
  background-color: #ffffff; /* 白色背景，讓內容更加突出 */
  padding: 20px; /* 添加內邊距 */
  border: 0.5px solid #003366; /* 設置邊框顏色和寬度 */
}

.v-list-item {
  border-radius: 8px;
  margin-bottom: 10px; /* 設定項目之間的間隔 */
}

.v-list-item:hover {
  background-color: #f0f0f0; /* 鼠標懸停時背景顏色 */
}

.v-list-item-title {
  font-weight: 500; /* 讓標題字體加粗 */
  font-size: 18px; /* 設定適中的字體大小 */
}

h2 {
  font-size: 24px;
  color: #003366; /* 主要顏色 */
  font-weight: 600; /* 加粗 */
  margin-bottom: 20px;
}

.title {
  color: black;
  font-weight: 600; /* 加粗 */
  font-size: 15px;
}

/* 移除聚焦時淺藍背景 */
.remove-focus-bg .v-field__overlay {
  background-color: transparent !important;
}
</style>
