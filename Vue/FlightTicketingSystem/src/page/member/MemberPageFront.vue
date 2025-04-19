<template>
  <v-row>
    <!-- 左侧区域 -->
    <v-col cols="12" md="3">
      <v-card class="mx-auto pa-2 no-shadow">
        <v-list>
          <h2>您好! 歡迎回來</h2>
          <v-list-subheader>{{ a }}</v-list-subheader>

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
      <v-card class="mx-auto" max-width="344">
        <v-card-text>
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
                <div>電子郵件</div>
                <span class="contentIn">{{ searchUser.email }}</span>
              </div>
            </section>
            <br />
            <hr />
            <section>
              <div>
                <div>剩餘里程</div>
                <span class="contentIn">{{
                  searchUser.totalMiles === 0
                    ? "尚未累積里程"
                    : searchUser.totalMiles
                }}</span>
              </div>
            </section>
            <br />
            <hr />
            <section>
              <div>
                <div>會員等級</div>
                <span class="contentIn">silver</span>
              </div>
            </section>
          </div>
        </v-card-text>

        <v-card-actions>
          <v-btn
            color="#003366"
            text="詳細資訊"
            variant="text"
            @click="show"
          ></v-btn>
        </v-card-actions>

        <v-expand-transition>
          <v-card
            v-if="reveal"
            class="position-absolute w-100 pa-4"
            height="100%"
            style="bottom: 0"
          >
            <v-row align="center">
              <v-avatar size="64" class="mr-4">
                <img
                  :src="searchUser.picture || '/images/default.png'"
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
        <v-list>
          <h2>另一個區域</h2>
          <v-list-item>
            <v-list-item-title>這是右側區域2的內容</v-list-item-title>
          </v-list-item>
        </v-list>
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
    <v-card-text v-if="detailControl && pageControl === 1">
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
          <v-row v-for="(field, index) in fields" :key="index" align="center">
            <v-col cols="5">
              <v-text-field
                v-model="field.value"
                :label="field.label"
                class="contentIn"
                variant="underlined"
                hide-details
                density="comfortable"
                readonly
              />
            </v-col>
            <v-col cols="7" class="text-left">
              <v-btn
                prepend-icon="mdi mdi-pencil"
                @click="editField(field, index)"
              >
                編輯
              </v-btn>
            </v-col>
          </v-row>
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
  a.value = authStore.user.username;

  if (a.value.includes("@")) {
    // 如果是郵箱地址，根據郵箱查找會員
    const email = encodeURIComponent(a.value);
    ApiMember.getMemberByEmail(email).then((res) => {
      if (res.status === 200) {
        searchUser.value = res.data;
        console.log(searchUser.value); // 確認是否正確返回資料
      } else {
        console.error("API 返回錯誤狀態碼:", res.status);
      }
    });
  } else {
    // 如果是用戶名，根據用戶名查找會員
    ApiMember.getMemberByUserName(a.value)
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
}

// 預設欄位資料
const fields = computed(() => [
  { label: "會員姓名", value: searchUser.value.fullName || "尚未填寫" },
  { label: "帳號", value: searchUser.value.username || "尚未填寫" },
  { label: "電子郵件", value: searchUser.value.email || "尚未填寫" },
  { label: "電話", value: searchUser.value.phoneNumber || "尚未填寫" },
]);

//彈出框

const dialog = ref(false);
const model = ref("原始資料");
const editLabel = ref();
const editValue = ref();
const defaultEditValue = ref();
const proxyModel = ref({
  value: "",
}); // proxyModel預設值

// // 這裡是監控 `proxyModel` 的變化，並且可以根據需求修改
// watch(proxyModel.value, (newVal) => {
//   console.log("proxyModel 更新:", newVal);
// });

function editField(field, index) {
  //開啟編輯視窗
  openEdit();

  proxyModel.value = { value: editValue.value }; // 更新 proxyModel 的內容
  console.log("index", index);

  // 編輯欄位的邏輯
  // console.log("編輯欄位:", field.value);
  //抓取field當中，第field個(field.label可以抓到編輯欄位!!!!!!!!!)
  editLabel.value = field.label;
  editValue.value = field.value;
  //抓取field當中，第field個(field.value可以抓到值!!!!!!!!!)
}

function show() {
  reveal.value = true;
  detailControl.value = true;
}

function hide() {
  reveal.value = false;
  detailControl.value = false;
}

// 開啟編輯視窗
function openEdit() {
  dialog.value = true;
}

function handleSave() {
  // 這裡可以抓到 model 的值()
  console.log("保存的值:", model);
  //先找到更改的資訊
  //把model.value指定給searchUser.value

  console.log("更改的項目:", editLabel.value);
  if (editLabel.value === "會員姓名") {
    searchUser.value.fullName = model.value;
  } else if (editLabel.value === "帳號") {
    searchUser.value.username = model.value;
  } else if (editLabel.value === "電子郵件") {
    searchUser.value.email = model.value;
  } else if (editLabel.value === "電話") {
    searchUser.value.phoneNumber = model.value;
  }
  // searchUser.value.fullName = model.value;
  console.log("更改的值:", editValue.value);

  ApiMember.updateMember(searchUser.value).then((res) => {
    if (res.status === 200) {
      console.log("更新成功");
      //放sweetalert成功
      Swal.fire({
        title: "更新成功!",
        icon: "success",
        draggable: true,
      });

      // 清空 model
      model.value = "";
      // 關閉對話框
      dialog.value = false;
    }
  });
}
//#endregion
</script>

<style scoped>
.no-shadow {
  box-shadow: none; /* 移除陰影 */
}

.contentIn {
  color: black;
  font-size: 18px;
}

.custom-hr {
  width: 80%; /* 設置為所需的寬度，這裡為 50% */
  margin: 0; /* 如果希望它居中，使用 auto */
  border: none; /* 移除原來的邊框樣式 */
  border-top: 2px solid #000; /* 設定上邊框的顏色與樣式 */
}

/* 編輯框置中 */
.overlay-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 9999; /* 確保在最上層 */
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.3); /* 半透明背景 */
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
