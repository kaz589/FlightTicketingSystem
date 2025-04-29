<!-- eslint-disable vue/max-attributes-per-line -->
<template>
  <v-layout>
    <v-navigation-drawer
      v-model="drawer"
      expand-on-hover
      rail
      @mouseenter="isHovered = true"
      @mouseleave="isHovered = false"
    >
      <v-list density="compact" item-props :items="items" nav>
        <template v-for="(item, index) in items">
          <v-list-item
            :key="index"
            v-if="item.visible"
            @click="$router.push(item.path)"
          >
            <v-icon :icon="item.prependIcon"></v-icon>
            <span v-if="isHovered">{{ item.title }}</span>
          </v-list-item>
        </template>
      </v-list>

      <template #append>
        <v-list-item
          class="ma-2"
          link
          nav
          prepend-icon="mdi-logout"
          title="登出"
          @click="logoutChange()"
        />
      </template>
    </v-navigation-drawer>

    <v-app-bar border="b" class="ps-4" flat>
      <v-app-bar-nav-icon
        v-if="$vuetify.display.smAndDown"
        @click="drawer = !drawer"
      />

      <v-app-bar-title class="ms-auto"
        ><img
          src="@/assets/Easytrip_text.png"
          alt="My Icon"
          width="13%"
          height="13%"
      /></v-app-bar-title>
      <h5 style="margin-left: auto">
        用戶名: {{ authStore.user ? authStore.user.username : "未登入" }}
      </h5>

      <template #append>
        <v-btn class="text-none me-2" height="48" icon slim>
          <v-avatar color="surface-light" size="32">
            <v-img
              :src="getPictureUrl(authStore.user?.picture)"
              alt="圖片未顯示"
            ></v-img>
          </v-avatar>

          <v-menu activator="parent">
            <v-list density="compact" nav>
              <!-- 跳轉至前台 -->
              <v-list-item
                append-icon="mdi-login"
                link
                title="回首頁"
                @click="backToMainPage(router)"
              />

              <v-list-item
                append-icon="mdi-cog-outline"
                link
                title="Settings"
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
      </template>
    </v-app-bar>

    <v-main>
      <div class="pa-4">
        <router-view></router-view>
      </div>
    </v-main>
  </v-layout>
</template>

<script setup>
import { onMounted, ref, watch, computed } from "vue";
import { useAuthStore } from "@/stores/auth"; // 引入 Pinia store
import { logout } from "@/utils/logout"; // 導入登出函數
import { useRouter } from "vue-router"; // 引入 vue-router
import { backToMainPage } from "@/utils/routerChange"; // 導入登出函數"

const router = useRouter(); // 使用 vue-router

onMounted(() => {
  console.log(authStore.user.authorityDetail);
  console.log("從後端回來的權限資料：", authStore.user.authorityDetail);
});

const authStore = useAuthStore(); // 使用 store
const userPicture = ref("");

//找到大頭貼
if (authStore.user && authStore.user.picture) {
  userPicture.value = authStore.user.picture;
} else {
  userPicture.value = "/images/default1.png"; // 預設圖片
}

const drawer = ref(true);
const isHovered = ref(false); // 追蹤懸停狀態
// 使用 watch 監控 drawer 的狀態變化
watch(drawer, (newVal, oldVal) => {
  console.log(
    `側邊欄狀態改變：從 ${oldVal ? "展開" : "縮起"} 到 ${
      newVal ? "展開" : "縮起"
    }`
  );
  // 在這裡可以執行其他邏輯，例如記錄狀態或觸發動畫
});

const logoutChange = () => {
  logout(); // 將 router 實例傳遞給 logout 函數
  router.push("/"); // 確保這行沒有問題，直接將用戶跳轉到登入頁(看需不需要回登入)
  console.log("登出成功");
};

//區分第三方登入和一般登入的圖片取得
function getPictureUrl(pic) {
  if (!pic) return "/images/default.png";
  if (pic.startsWith("http")) return pic;
  return "http://localhost:8080" + pic;
}

//權限判斷
const hasAuthority = (role) => {
  if (!authStore.user?.authorityDetail) return false;
  return authStore.user.authorityDetail.split(",").includes(role);
};

const RoleCount = computed(() => {
  if (authStore.hasRole("MANAGER")) return 3;
  if (authStore.hasRole("ADMIN")) return 2;
  if (authStore.hasRole("USER")) return 1;
  return 0;
});

const items = computed(() => [
  {
    title: "登入畫面",
    prependIcon: "mdi-view-dashboard-outline",
    link: true,
    path: "/login",
    visible: false,
  },
  {
    title: "會員管理",
    prependIcon: "mdi mdi-account-group",
    link: true,
    path: "/admin/members",
    visible: hasAuthority("會員"),
  },
  {
    title: "機場管理",
    prependIcon: "mdi-airport",
    link: true,
    path: "/admin/airport",
    visible: hasAuthority("航線"),
  },
  {
    title: "航線管理",
    prependIcon: "mdi mdi-airplane-takeoff",
    link: true,
    path: "/admin/flight",
    visible: hasAuthority("航線"),
  },
  {
    title: "票務訂單管理",
    prependIcon: "mdi mdi-ticket",
    link: false,
    path: "/admin/Ticket",
    visible: hasAuthority("航線"),
  },
  {
    title: "座位管理",
    prependIcon: "mdi mdi-seat",
    link: false,
    path: "/admin/Seats",
    visible: hasAuthority("航線"),
  },
  {
    title: "旅行管理",
    prependIcon: "mdi mdi-island",
    link: false,
    path: "/admin/travel",
    visible: hasAuthority("旅遊"),
  },
  {
    title: "商品管理",
    prependIcon: "mdi mdi-gift-outline",
    link: true,
    path: "/admin/products",
    visible: hasAuthority("產品"),
  },
  {

    title: "里程兌換訂單管理",
    prependIcon: "mdi mdi-clipboard-list",
    link: true,
    path: "/admin/redeemmanage",
    visible: hasAuthority("產品"),
  },
  {
    title: "權限管理",
    prependIcon: "mdi mdi-shield-lock-outline",
    link: true,
    path: "/admin/authority",
    visible: RoleCount.value == 3,

  },
]);
</script>
