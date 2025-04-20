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
                <div class="row">
                  <div class="col-md-6 mb-4">
                    <div data-mdb-input-init class="form-outline">
                      <input
                        type="text"
                        id="form3Example1"
                        class="form-control"
                        v-model="signUpData.username"
                      />
                      <label class="form-label" for="form3Example1">帳號</label>
                    </div>
                  </div>
                  <div class="col-md-6 mb-4">
                    <div data-mdb-input-init class="form-outline">
                      <input
                        type="password"
                        id="form3Example2"
                        class="form-control"
                        v-model="signUpData.password"
                      />
                      <label class="form-label" for="form3Example2">密碼</label>
                    </div>
                  </div>
                </div>

                <!-- Email input -->
                <div data-mdb-input-init class="form-outline mb-4">
                  <input
                    type="email"
                    id="form3Example3"
                    class="form-control"
                    v-model="signUpData.email"
                  />
                  <label class="form-label" for="form3Example3">信箱</label>
                </div>

                <!-- Password input -->
                <div data-mdb-input-init class="form-outline mb-4">
                  <input
                    type="text"
                    id="form3Example4"
                    class="form-control"
                    v-model="signUpData.phoneNumber"
                  />
                  <label class="form-label" for="form3Example4">電話</label>
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
import { ref } from "vue";
import Swal from "sweetalert2";
import { useRouter } from "vue-router";

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
</style>
