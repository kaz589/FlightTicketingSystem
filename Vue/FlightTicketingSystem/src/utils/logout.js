import { useAuthStore } from "@/stores/auth";  // 引入 Pinia store
import { useRouter } from "vue-router";  // 引入 vue-router


export function logout() {
    const authStore = useAuthStore();
    const router = useRouter();
    authStore.logout();  // 更新 Pinia 狀態為未登入
    console.log("成功登出");

    router.push({ path: "/login" });  // 跳轉到登入頁面
  }