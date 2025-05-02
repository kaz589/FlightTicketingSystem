<template>
  <v-app>
    <header class="sticky top-0 z-50 bg-white shadow-sm py-4 px-4">
      <div class="flex items-center justify-between w-full">
        <div class="flex items-center">
          <img
            src="@/assets/Easytrip_text.png"
            alt="Easytrip Logo"
            class="w-40 h-auto object-contain transition-transform duration-300 hover:scale-105"
            @click="router.push('/')"
          />
        </div>
        <nav
          class="hidden md:flex space-x-8 text-3xl font-semibold"
          style="margin-left: 6em"
        >
          <a
            href="#"
            class="relative group text-gray-700 hover:text-black transition-colors duration-200"
          >
            探索
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-black transition-all duration-300 group-hover:w-full"
            ></span>
          </a>
          <a
            href="#"
            class="relative group text-gray-700 hover:text-black transition-colors duration-200"
          >
            會員
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-black transition-all duration-300 group-hover:w-full"
            ></span>
          </a>
          <a
            href="#"
            class="relative group text-gray-700 hover:text-black transition-colors duration-200"
          >
            里程
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-black transition-all duration-300 group-hover:w-full"
            ></span>
          </a>
        </nav>

        <div class="flex justify-end items-center space-x-2 text-base">
          <span class="mdi mdi-web text-[40px]"></span>

          <span
            ><img src="https://flagcdn.com/tw.svg" width="40" alt="Taiwan"
          /></span>
          <span class="text-[35px]">TWD</span>

          <v-btn
            @click="router.push('/checkout')"
            class="text-none"
            variant="flat"
            stacked
          >
            <v-badge
              color="error"
              :content="productTypesCount"
              :model-value="productTypesCount > 0"
            >
              <v-icon size="36">mdi mdi-cart-variant</v-icon>
            </v-badge>
          </v-btn>
          <span class="text-[35px]">{{ roleLabel }}</span>
          <button
            class="login-btn"
            @click="router.push('/loginUser')"
            v-if="showLoginBtn"
          >
            <span>登入</span>
          </button>

          <v-btn
            class="text-none me-2"
            height="48"
            icon
            slim
            variant="flat"
            v-if="showUserBtn > 0"
          >
            <v-avatar color="surface-light" size="32">
              <img :src="getPictureUrl(userPicture)" alt="圖片未顯示" />
            </v-avatar>

            <v-menu v-model="menuVisible" activator="parent" persistent>
              <v-list density="compact" nav>
                <v-list-item
                  append-icon="mdi mdi-login"
                  link
                  title="後台首頁"
                  @click="router.push('/admin')"
                  v-if="showUserBtn >= 2"
                />
                <v-list-item
                  append-icon="mdi mdi-account"
                  link
                  title="個人頁面"
                  @click="userPage"
                />

                <v-list-item
                  append-icon="mdi-logout"
                  link
                  title="登出"
                  @click="logoutChange()"
                />
              </v-list>
            </v-menu>
          </v-btn>
        </div>
      </div>
    </header>

    <v-main>
      <div class="pa-4">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" :key="$route.fullPath" />
          </transition>
        </router-view>
        <Footer />
      </div>
    </v-main>
  </v-app>
</template>

<script setup>
import { ref, onMounted, computed, watch } from "vue";
import { useRouter } from "vue-router"; // 引入 vue-router

import { storeToRefs } from "pinia";
import { useAuthStore } from "@/stores/auth"; // 引入 Pinia store
import { logout } from "@/utils/logout"; // 導入登出函數
import Footer from "./Footer.vue";
import { usecartStore } from "@/stores/usecartStore";

const router = useRouter(); // 使用 vue-router
const authStore = useAuthStore();

const cartStore = usecartStore();
const { productTypesCount } = storeToRefs(cartStore);
const userPicture = ref("");
//找到大頭貼
if (authStore.user && authStore.user.picture) {
  userPicture.value = authStore.user.picture;
}

watch(
  () => authStore.user?.picture,
  (newPicture) => {
    if (newPicture) {
      userPicture.value = newPicture;
    }
  },
  { immediate: true } // 立即執行一次
);

// 👉 根據 store 判斷是否顯示登入按鈕
const showLoginBtn = computed(() => !authStore.isAuthenticated);

// 👉 根據角色來判斷顯示 User/Admin 按鈕
const showUserBtn = computed(() => {
  if (authStore.hasRole("MANAGER")) return 3;
  if (authStore.hasRole("ADMIN")) return 2;
  if (authStore.hasRole("USER")) return 1;
  return 0;
});

onMounted(() => {
  authStore.checkLoginStatus();
  console.log("isAuthenticated:", authStore.isAuthenticated);
  console.log("大頭貼 URL:", getPictureUrl(userPicture.value));
});

//計算是什麼角色
const roleLabel = computed(() => {
  if (showUserBtn.value === 3) return "主管";
  if (showUserBtn.value === 2) return "管理員";
  if (showUserBtn.value === 1) return "會員";
  return "";
});

const menuVisible = ref(false);
const logoutChange = () => {
  logout(); // 將 router 實例傳遞給 logout 函數
  router.push("/");
  console.log("登出成功");
};

//測試當前狀態
// function test() {
//   const role = localStorage.getItem("roles");
//   const user = JSON.parse(localStorage.getItem("user"));
//   const isLoggedIn = localStorage.getItem("isLoggedIn");
//   console.log(isLoggedIn);
//   console.log(user);
//   console.log(role);
// }

function userPage() {
  router.push("/memberFront");
}

function getPictureUrl(pic) {
  console.log(pic);

  if (!pic || pic === "null" || pic === "undefined")
    return "/images/default1.png";
  if (pic.startsWith("http")) return pic;
  return "http://localhost:8080" + pic;
}
</script>

<style>
/* a span {
  display: block;
  height: 2px;
  background: black;
  transition: width 0.3s ease;
} */
.login-btn {
  padding: 8px 16px;
  border: 1px solid transparent;
  background-color: white;
  transition: all 0.3s;
  border-radius: 4px;
  cursor: pointer;
}

.login-btn:hover {
  border-color: #ccc; /* 灰色邊框 */
  box-shadow: 0 0 5px #ccc; /* 灰色陰影 */
}
</style>
