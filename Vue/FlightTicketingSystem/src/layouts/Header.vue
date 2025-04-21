<template>
    <v-app>
  <header class="sticky top-0 z-50 bg-white shadow-sm py-4 px-4">
    <div class="max-w-screen-xl mx-auto flex items-center justify-between">
      <div class="flex items-center">
        <img
          src="@/assets/Easytrip_text.png"
          alt="Easytrip Logo"
          class="w-36 h-auto object-contain transition-transform duration-300 hover:scale-105"
        />
      </div>
      <nav class="hidden md:flex space-x-8 text-base font-semibold ml-12">
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
      <div class="flex items-center gap-4 text-base">
        <span class="mdi mdi-web text-xl text-gray-700"></span>
        <img
          src="https://flagcdn.com/tw.svg"
          width="30"
          alt="Taiwan"
          class="rounded transition-transform duration-300 hover:scale-105"
        />
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
    </div>
  </header>
  <v-main>
      <div class="pa-4">
        <router-view></router-view>
      </div>
    </v-main>
</v-app>
</template>

<script setup>
import { ref } from 'vue';
const menuVisible = ref(false);</script>

<style scoped>
a span {
  display: block;
  height: 2px;
  background: black;
  transition: width 0.3s ease;
}
</style>
