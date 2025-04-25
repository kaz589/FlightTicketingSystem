<template>
  <!-- Section: Design Block -->
  <section class="text-center text-lg-start">
    <!-- Jumbotron -->
    <div class="container py-4">
      <div class="row g-0 align-items-center">
        <div class="col-lg-6 mb-5 mb-lg-0">
          <div
            class="card cascading-right bg-body-tertiary"
            style="backdrop-filter: blur(30px)"
          >
            <div class="card-body p-5 shadow-5 text-center">
              <h2 class="fw-bold mb-5">會員註冊</h2>
              <form>
                <!-- 2 column grid layout with text inputs for the first and last names -->

                <div data-mdb-input-init class="form-outline mb-4">
                  <input
                    type="text"
                    id="form3Example1"
                    class="form-control active"
                    v-model="signUpData.username"
                    @input="validateUsername"
                  />
                  <label class="form-label" for="form3Example1">帳號</label>
                  <small v-if="errors.username" class="text-danger">{{
                    errors.username
                  }}</small>
                </div>

                <div data-mdb-input-init class="form-outline mb-4">
                  <input
                    type="password"
                    id="form3Example2"
                    class="form-control active"
                    v-model="signUpData.password"
                    @input="validatePassword"
                  />
                  <label class="form-label" for="form3Example2">密碼</label>
                  <small v-if="errors.password" class="text-danger">{{
                    errors.password
                  }}</small>
                </div>

                <div data-mdb-input-init class="form-outline mb-4">
                  <input
                    type="password"
                    id="form3Example5"
                    class="form-control active"
                    v-model="PasswordDoubleCheck"
                    @blur="validatePasswordDoubleCheck"
                  />
                  <label class="form-label" for="form3Example2">確認密碼</label>
                  <small
                    v-if="errors.passwordDoubleCheck"
                    class="text-danger"
                    >{{ errors.passwordDoubleCheck }}</small
                  >
                </div>

                <!-- Email input -->
                <div data-mdb-input-init class="form-outline mb-4">
                  <input
                    type="email"
                    id="form3Example3"
                    class="form-control active"
                    v-model="signUpData.email"
                    @input="validateEmail"
                  />
                  <label class="form-label" for="form3Example3">信箱</label>
                  <small v-if="errors.email" class="text-danger">{{
                    errors.email
                  }}</small>
                </div>

                <!-- Password input -->
                <div data-mdb-input-init class="form-outline mb-4">
                  <input
                    type="text"
                    id="form3Example4"
                    class="form-control active"
                    v-model="signUpData.phoneNumber"
                    @input="validatePhoneNumber"
                  />
                  <label class="form-label" for="form3Example4">電話</label>
                  <small v-if="errors.phoneNumber" class="text-danger">{{
                    errors.phoneNumber
                  }}</small>
                </div>

                <!-- Submit button -->
                <button
                  type="submit"
                  data-mdb-button-init
                  data-mdb-ripple-init
                  class="btn btn-primary btn-block mb-4"
                  @click="save"
                >
                  註冊
                </button>
                <!-- <button
                  type="submit"
                  data-mdb-button-init
                  data-mdb-ripple-init
                  class="btn btn-primary btn-block mb-4"
                  @click="goToLogin"
                >
                  回登入畫面
                </button> -->
              </form>
            </div>
          </div>
        </div>

        <div class="col-lg-6 mb-5 mb-lg-0">
          <img
            src="https://mdbootstrap.com/img/new/ecommerce/vertical/004.jpg"
            class="w-100 rounded-4 shadow-4"
            alt=""
          />
        </div>
      </div>
    </div>
    <!-- Jumbotron -->
  </section>
  <!-- Section: Design Block -->
</template>

<script setup>
import { ApiMember } from "@/utils/API";
import { ref, computed } from "vue";
import Swal from "sweetalert2";
import { useRouter } from "vue-router";

const PasswordDoubleCheck = ref("");
//錯誤處理###########

const errors = ref({});
function validateForm() {
  errors.value = {}; // 清空錯誤

  validateUsername();
  validatePassword();
  validateEmail();
  validatePhoneNumber();
  validatePasswordDoubleCheck();

  return Object.keys(errors.value).length === 0;
}

//拆分成各自處理
//username
function validateUsername() {
  if (!signUpData.value.username) {
    errors.value.username = "帳號為必填";
  } else {
    delete errors.value.username;
  }
}
//password
function validatePassword() {
  if (!signUpData.value.password || signUpData.value.password.length < 6) {
    errors.value.password = "密碼至少 6 碼";
  } else {
    delete errors.value.password;
  }
}
//email
function validateEmail() {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(signUpData.value.email)) {
    errors.value.email = "請輸入有效的 Email";
  } else {
    delete errors.value.email;
  }
}
//phone
function validatePhoneNumber() {
  const phoneRegex = /^09\d{8}$/;
  if (!phoneRegex.test(signUpData.value.phoneNumber)) {
    errors.value.phoneNumber = "請輸入正確的手機號碼格式（09 開頭共 10 碼）";
  } else {
    delete errors.value.phoneNumber;
  }
}

//密碼驗證
function validatePasswordDoubleCheck() {
  console.log("確認密碼為 : " + PasswordDoubleCheck.value);
  console.log("密碼為 : " + signUpData.value.password);
  //如果確認密碼與密碼不一致
  if (PasswordDoubleCheck.value != signUpData.value.password) {
    errors.value.passwordDoubleCheck = "密碼需一致";
  } else {
    delete errors.value.passwordDoubleCheck;
  }
}

//錯誤處理區塊###########

const DEFAULT_FORM = ref({
  username: "",
  password: "",
  email: "",
  phoneNumber: "",
  registrationDate: new Date().toISOString().slice(0, 10), // 自動填入今天日期
  authority: "USER",
});
const signUpData = ref({ ...DEFAULT_FORM.value });
const router = useRouter();

function save() {
  event.preventDefault(); // ⛔ 阻止 submit
  console.log("準備註冊");

  //如果不符合規定 不給註冊
  if (!validateForm()) {
    // 顯示錯誤訊息
    // const errorMessages = Object.values(errors.value).join("<br>");
    // Swal.fire({
    //   icon: "error",
    //   title: "請按照正確格式填寫",
    //   html: errorMessages,
    // });
    return;
  }

  //   console.log(signUpData.value.username);
  //   console.log(signUpData.value.password);
  ApiMember.insertMember(signUpData.value).then((res) => {
    console.log(res);
    if (res === null) {
      //   alert("帳號名稱已被使用");
      Swal.fire({
        icon: "error",
        title: "帳號名稱已被使用",
        text: "請更換一個新的名稱",
      });
      //   test.value = "使用者重複";
    } else {
      Swal.fire("註冊成功", "", "success"); // 顯示成功的提示框
      router.push("/loginUser");
    }
  });
}

function goToLogin() {
  event.preventDefault(); // ⛔ 阻止 submit
  router.push("/loginUser");
}
</script>

<style scoped>
.cascading-right {
  margin-right: -50px;
}

@media (max-width: 991.98px) {
  .cascading-right {
    margin-right: 0;
  }
}

.form-outline {
  position: relative;
  margin-bottom: 1rem;
  border: 1px solid #ccc;
  border-radius: 0.375rem;
  padding: 0.375rem;
}

/* .form-control {
  border-radius: 0.375rem;
  border: 1px solid #ccc;
  padding: 0.5rem;
} */
.form-label {
  position: absolute;
  top: -0.75rem;
  left: 0.75rem;
  background-color: white;
  padding: 0 0.25rem;
  font-size: 0.875rem;
  color: #555;
}

.error-message-left {
  color: red;
  margin-right: 8px;
  white-space: nowrap;
  font-size: 0.75rem;
}
</style>
