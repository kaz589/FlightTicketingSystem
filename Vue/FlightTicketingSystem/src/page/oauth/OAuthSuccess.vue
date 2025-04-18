<template>
  <div>
    <h1>跳轉中</h1>
    <!-- <p>Token: {{ token }}</p> -->
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { jwtDecode } from "jwt-decode"; //解析JWT
import { cleanRole } from "@/utils/roleHelper";
import { useAuthStore } from "@/stores/auth"; // 引入 Pinia store
import Swal from "sweetalert2";

const route = useRoute();
const router = useRouter();
const token = ref("");
// Pinia store 實例
const authStore = useAuthStore();

// 當頁面載入時，從網址中取得 token
onMounted(() => {
  const queryToken = route.query.token;
  if (queryToken) {
    token.value = queryToken;

    // 儲存到 localStorage（或 pinia/vuex 也可）
    localStorage.setItem("token", queryToken);
    console.log(token.value);

    // console.log(queryToken);

    // 解析JWT;
    const payload = jwtDecode(queryToken); // 解碼 JWT
    console.log("JWT Payload:", payload.roles); //確認角色有哪些
    const testUser = {
      username: payload.sub,
    };
    const clean = cleanRole(payload); //取得乾淨角色
    console.log(testUser);

    //登入成功(無論是甚麼角色都更改成login狀態)，放角色進入pinia
    authStore.login(testUser, token.value, clean); // 更新 Pinia 狀態為已登入，並儲存用戶資料  並放入token以及乾淨角色

    //登錄後跳轉到指定頁面;
    router.push("/");
    // 也可以選擇導向其他頁面：
    // router.push("/dashboard");
    Swal.fire({
      title: "登入成功!",
      icon: "success",
      draggable: true,
    });
  } else {
    token.value = "❌ 未收到 Token，可能登入流程有誤";
  }
});
</script>

<style scoped></style>
