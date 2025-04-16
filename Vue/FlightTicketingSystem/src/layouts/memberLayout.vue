<template>
  <v-app>
    <v-app-bar class="app-bar">
      <!-- Left: Logo -->
      <v-app-bar-title>
        <img
          src="@/assets/Easytrip_text.png"
          alt="Easytrip Logo"
          width="25%"
          height="40%"
        />
      </v-app-bar-title>

      <!-- Middle: Navigation -->
      <!-- Tabs -->
      <v-tabs align-tabs="title" class="tabs">
        <v-tab v-for="tab in tabs" :text="tab.name" @click="$router.push(tab.path)">
        
            <!-- Icon -->
            <v-icon :class="['mdi', tab.icon]" style="font-size: 30px;"></v-icon>
            <!-- Name -->
            <span style="font-size: 25px">{{ tab.name }}</span>
          
        </v-tab>
      </v-tabs>

      <!-- Right: Language & Avatar -->
      <div class="flex justify-end items-center space-x-4 text-lg">
        <span class="mdi mdi-web text-[30px]"></span>
        <span
          ><img src="https://flagcdn.com/tw.svg" width="30" alt="Taiwan"
        /></span>
        <span>TWD</span>
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
    </v-app-bar>
<br>
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
.app-bar {
  height: 80px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.tabs {
  display: flex;
  justify-content: center;
  flex-grow: 1;
}
</style>
