<template>
  <v-app>
    <v-app-bar>
      <header class="grid grid-cols-3 items-center py-6">
        <!-- Left: Logo -->
        <div class="flex justify-start">
          <img
            src="@/assets/Easytrip_text.png"
            alt="Easytrip Logo"
            width="40%"
            height="40%"
          />
        </div>

        <!-- Middle: Navigation -->
        <!-- Tabs -->
        <div
          class="flex justify-start space-x-6 text-base font-semibold mb-1 mt-4"
        >
          <div v-for="tab in tabs" :key="tab.name">
            <v-btn @click="$router.push(tab.path)">
              <!-- Icon -->
              <i :class="['mdi', tab.icon]"></i>
              <!-- Name -->
              <span>{{ tab.name }}</span>
            </v-btn>
          </div>
        </div>

        <!-- Right: Language & Avatar -->
        <div class="flex justify-end items-center space-x-4 text-base">
          <span class="mdi mdi-web text-[30px]"></span>
          <span
            ><img src="https://flagcdn.com/tw.svg" width="30" alt="Taiwan"
          /></span>
          <span>TWD</span>
          <button class="login-btn" @click="router.push('/loginUser')">
            <span>登入</span>
          </button>
          <v-btn class="text-none me-2" height="48" icon slim>
            <v-avatar color="surface-light" class="mdi mdi-account" size="32" />

            <v-menu v-model="menuVisible" activator="parent" persistent>
              <v-list density="compact" nav>
                <v-list-item
                  append-icon="mdi mdi-login"
                  link
                  title="管理員登入"
                  @click="router.push('/login')"
                />

                <v-list-item
                  append-icon="mdi mdi-account"
                  link
                  title="個人頁面"
                  @click="router.push('/memberFront')"
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
      </header>
    </v-app-bar>
    <v-main>
      <div class="pa-4">
        <router-view></router-view>
      </div>
    </v-main>
  </v-app>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router"; // 引入 vue-router
const router = useRouter(); // 使用 vue-router
const tabs = ref([
  {
    name: "航班",
    icon: "mdi-airplane",
    path: "/",
  },
  { name: "禮品", icon: "mdi-gift", path: "/test2" },
  { name: "景點", icon: "mdi-map-marker-radius", path: "/test3" },
]);
const selectedTab = ref(tabs.value[0].name);
const menuVisible = ref(false);
function logoutChange() {
  // 登出邏輯
  console.log("User logged out");
}
</script>

<style scoped>
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
