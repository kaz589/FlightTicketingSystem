<template>
  <section class="vh-100">
    <div class="container py-5 h-100">
      <div class="row d-flex align-items-center justify-content-center h-100">
        <div class="col-md-8 col-lg-7 col-xl-6">
          <img
            src="@/assets/Easytrip_logo.png"
            class="img-fluid"
            alt="Phone image"
          />
        </div>
        <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1">
          <form>
            <!-- Email input -->
            <div data-mdb-input-init class="form-outline mb-4">
              <input
                type="text"
                id="form1Example13"
                class="form-control form-control-lg"
                v-model="rawData.username"
              />
              <label class="form-label" for="form1Example13">帳號</label>
            </div>

            <!-- Password input -->
            <div data-mdb-input-init class="form-outline mb-4">
              <input
                type="password"
                id="form1Example23"
                class="form-control form-control-lg"
                v-model="rawData.password"
              />
              <label class="form-label" for="form1Example23">密碼</label>
            </div>

            <div class="d-flex justify-content-around align-items-center mb-4">
              <!-- Checkbox -->
              <div class="form-check">
                <input
                  class="form-check-input"
                  type="checkbox"
                  value=""
                  id="form1Example3"
                  checked
                />
                <label class="form-check-label" for="form1Example3">
                  記住帳號
                </label>
              </div>
              <a href="#!">忘記密碼?</a>
            </div>

            <!-- Submit button -->
            <button
              type="submit"
              data-mdb-button-init
              data-mdb-ripple-init
              class="btn btn-primary btn-lg btn-block"
              @click="authentication"
            >
              登入
            </button>

            <div class="divider d-flex align-items-center my-4">
              <p class="text-center fw-bold mx-3 mb-0 text-muted">或</p>
            </div>

            <a
              data-mdb-ripple-init
              class="btn btn-primary btn-lg btn-block"
              style="background-color: #3b5998"
              href="http://localhost:8080/oauth2/authorization/facebook"
              role="button"
            >
              <i class="fab fa-facebook-f me-2"></i>使用FACEBOOK登入
            </a>
            <a
              data-mdb-ripple-init
              class="btn btn-primary btn-lg btn-block"
              style="background-color: #dd4b39"
              href="http://localhost:8080/oauth2/authorization/google"
              role="button"
            >
              <i class="fab fa-google me-2"></i>使用GOOGLE登入</a
            >
          </form>
          <br />
          <br />

          <div class="d-flex justify-content-center">
            <p class="mb-0">
              還沒有帳號?
              <router-link to="/Signup">註冊</router-link>
            </p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/auth"; // 引入 Pinia store
import { ApiMember } from "@/utils/API";
import { jwtDecode } from "jwt-decode";
import { cleanRole } from "@/utils/roleHelper";
import Swal from "sweetalert2";
//登入

// Pinia store 實例
const authStore = useAuthStore();

//首先先綁定帳號密碼下來
const DEFAULT_SEARCH = ref({
  username: "",
  password: "",
});
const router = useRouter();
const rawData = ref({ ...DEFAULT_SEARCH.value });

function authentication() {
  event.preventDefault(); // ⛔ 阻止 submit

  if (rawData.value.username === "" || rawData.value.password === "") {
    Swal.fire({
      icon: "error",
      title: "帳號密碼不能為空",
    });
    return;
  }

  console.log(rawData.value.username);
  console.log(rawData.value.password);
  //登入取得JWT
  ApiMember.login(rawData.value)
    .then((res) => {
      console.log(res.data.token);
      if (res.data) {
        //如果有res.data
        const token = res.data.token; //接住token
        const payload = jwtDecode(token); // 解碼 JWT
        console.log("JWT Payload:", payload.roles); //確認角色有哪些
        const testUser = {
          username: payload.sub,
        }; //使用者名稱

        const clean = cleanRole(payload); //取得乾淨角色
        console.log(clean);

        //登入成功(無論是甚麼角色都更改成login狀態)，放角色進入pinia
        authStore.login(testUser, token, clean); // 更新 Pinia 狀態為已登入，並儲存用戶資料  並放入token以及乾淨角色
        console.log("儲存角色為" + localStorage.getItem("roles")); //可以透過localStorage.getItem("roles")取出角色

        //登錄後跳轉到指定頁面;
        router.push("/");
        Swal.fire({
          title: "登入成功!",
          icon: "success",
          draggable: true,
        });
      }
    })
    .catch((error) => {
      if (error.response.status === 403) {
        Swal.fire({
          icon: "error",
          title: "密碼不符合",
          text: "請確認密碼是否輸入正確",
        });
      }
    });
}

//登入錯誤處理
const urlParams = new URLSearchParams(window.location.search);
if (urlParams.get("error") === "oauth2_failed") {
  alert("Facebook 登入被取消或發生錯誤，請再試一次！");
}
</script>

<style scoped>
.divider:after,
.divider:before {
  content: "";
  flex: 1;
  height: 1px;
  background: #eee;
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
