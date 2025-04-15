<template>
  <v-container class="login-container" fluid>
    <v-row justify="center" align="center" class="fill-height">
      <!-- 主容器 -->
      <v-col cols="12" md="6" lg="4">
        <!-- 標題 -->
        <v-row class="mb-4" justify="center">
          <v-col cols="12">
            <h1 class="text-center">管理員登入</h1>
          </v-col>
        </v-row>

        <!-- 登入表單 -->
        <v-form>
          <v-row>
            <v-col cols="12">
              <v-text-field
                v-model="rawData.username"
                :counter="20"
                label="會員帳號"
                required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12">
              <v-text-field
                v-model="rawData.password"
                :counter="20"
                label="會員密碼"
                required
                type="password"
              ></v-text-field>
            </v-col>
          </v-row>
          <v-btn prepend-icon="mdi mdi-login" @click="authentication">
            登入
          </v-btn>

          <v-btn prepend-icon="mdi mdi-account-plus" @click="insertOpen">
            註冊
          </v-btn>

          <v-btn
            prepend-icon="mdi mdi-account-plus"
            @click="backToMainPage(router)"
          >
            返回首頁
          </v-btn>

          <div class="button-test">
            <button @click="login" v-if="!isAuthenticated">登入</button>
            <button @click="logout" v-if="isAuthenticated">登出</button>
          </div>
        </v-form>

        <!-- 註冊按鈕 -->

        <!-- 彈出框html -->
        <v-dialog v-model="dialog" max-width="600">
          <v-card>
            <v-card-title>新增管理員</v-card-title>
            <v-card-text>
              <v-form>
                <v-row>
                  <v-col cols="12">
                    <v-text-field
                      v-model="insertData.fullName"
                      label="全名"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                      v-model="insertData.username"
                      label="使用者名稱"
                      required
                      :error-messages="test"
                      @focus="focusUsername"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                      v-model="insertData.password"
                      label="使用者密碼"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                      v-model="insertData.email"
                      label="信箱"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                      v-model="insertData.phoneNumber"
                      label="電話"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                      v-model="insertData.registrationDate"
                      label="註冊時間"
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
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, shallowRef, computed } from "vue";
import { useRouter } from "vue-router";
import { ApiAdmin, ApiMember } from "@/utils/API";
import Swal from "sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";
import { useAuthStore } from "@/stores/auth"; // 引入 Pinia store
import { backToMainPage } from "@/utils/routerChange";
import { jwtDecode } from "jwt-decode";

// Pinia store 實例
const authStore = useAuthStore();

// 將 isAuthenticated 提取為 computed 屬性，方便模板中使用
const isAuthenticated = computed(() => authStore.isAuthenticated);

//登入
const DEFAULT_SEARCH = ref({
  username: "",
  password: "",
});
const router = useRouter();
const rawData = ref({ ...DEFAULT_SEARCH.value });

// function authentication() {
//   console.log("驗證過程");
//   console.log("帳號 : " + rawData.value.username);
//   console.log("密碼 : " + rawData.value.password);
//   ApiAdmin.login(rawData.value)
//     .then((res) => {
//       //做登入

//       //回傳直res.data如果是true則執行登入
//       if (res.data) {
//         // console.log(res.data);
//         const testUser = {
//           username: rawData.value.username,
//         };
//         authStore.login(testUser); // 更新 Pinia 狀態為已登入，並儲存用戶資料
//         console.log("成功登入", testUser);

//         // 登錄後跳轉到指定頁面
//         router.push("/admin");
//         Swal.fire({
//           title: "登入成功!",
//           icon: "success",
//           draggable: true,
//         });
//       } else {
//         Swal.fire({
//           icon: "error",
//           title: "密碼不符合",
//           text: "請確認密碼是否輸入正確",
//         });
//       }
//     })
//     .catch((error) => {
//       // 處理錯誤
//       Swal.fire({
//         icon: "error",
//         title: "登入失敗",
//         text: "請確認帳號密碼",
//       });
//     });
// }

function authentication() {
  console.log(rawData.value);
  //登入取得JWT
  ApiMember.login(rawData.value)
    .then((res) => {
      if (res.data) {
        console.log(res.data.token);
        const token = res.data.token;
        const payload = jwtDecode(token);
        console.log("JWT Payload:", payload.sub);
        const testUser = {
          username: payload.sub,
        };
        authStore.login(testUser, token); // 更新 Pinia 狀態為已登入，並儲存用戶資料  並放入token
        //登錄後跳轉到指定頁面;
        router.push("/admin");
        Swal.fire({
          title: "登入成功!",
          icon: "success",
          draggable: true,
        });
      } else {
        Swal.fire({
          icon: "error",
          title: "密碼不符合",
          text: "請確認密碼是否輸入正確",
        });
      }
    })
    //確認錯誤
    .catch((error) => {
      if (error.response.status === 403) {
        Swal.fire({
          icon: "error",
          title: "密碼不符合",
          text: "請確認密碼是否輸入正確",
        });
      }

      if (error.response) {
        console.error("Error status:", error.response.status);
        console.error("Error details:", error.response.data);
      } else {
        console.error("Unexpected error:", error.message);
      }
    });
}

//註冊
const DEFAULT_FORM = ref({
  fullName: "1",
  username: "11",
  password: "1",
  email: "1",
  phoneNumber: "1",
  registrationDate: "2010-11-11",
});

const insertData = ref({ ...DEFAULT_FORM.value });
const dialog = shallowRef(false); //彈出對話框預設不彈出

function insertOpen() {
  console.log("註冊表單開啟");
  dialog.value = true; //打開彈出框
}

const test = ref("");

function focusUsername() {
  test.value = "";
}

function save() {
  //透過api更改
  ApiAdmin.insertAdmin(insertData.value).then((res) => {
    console.log(res);
    //如果res是 null, 顯示新增失敗(使用者重複)
    if (res === null) {
      // alert("使用者重複");
      test.value = "使用者重複";
    } else {
      Swal.fire("更新成功", "", "success"); // 顯示成功的提示框
      dialog.value = false; //關閉彈出框
    }
  });
}

// // 登入測試
// function login() {
//   const testUser = { username: "testuser", email: "testuser@example.com" };
//   authStore.login(testUser); // 更新 Pinia 狀態為已登入，並儲存用戶資料
//   console.log("成功登入", testUser);

//   // 登錄後跳轉到指定頁面
//   router.push({ path: "/admin" });
// }

// // 登出測試
// function logout() {
//   authStore.logout(); // 更新 Pinia 狀態為未登入

//   console.log("成功登出");
// }
</script>

<style scoped>
.login-container {
  height: 100vh;
}

v-card {
  padding: 20px;
}

.v-alert {
  margin-top: 20px;
}

h1 {
  font-size: 2rem;
  font-weight: bold;
  color: #000306; /* Vuetify primary color */
  margin-bottom: 20px;
}

.button-test {
  display: none;
}
</style>
