<template>
    <v-layout>
      <v-navigation-drawer v-model="drawer">
        <v-list density="compact" item-props nav>
          <v-list-item v-for="item in items" @click="$router.push(item.path)">
            <v-icon :icon="item.prependIcon"></v-icon> {{ item.title }}
          </v-list-item>
        </v-list>
  
        <template #append>
          <v-list-item
            class="ma-2"
            link
            nav
            prepend-icon="mdi-cog-outline"
            title="Settings"
          />
        </template>
      </v-navigation-drawer>
  
      
  
      <v-main>
        <div class="pa-4">
          <router-view></router-view>
        </div>
      </v-main>
    </v-layout>
  </template>
  
  <script setup>
  import { ref } from "vue";
  
  const drawer = ref(true);
  
  const items = ref([
    {
      title: "登入畫面",
      prependIcon: "mdi-view-dashboard-outline",
      link: true,
      path: "/login",
    },
    {
      title: "會員管理",
      prependIcon: "mdi mdi-account-group",
      link: true,
      path: "/members",
    },
    {
      title: "機場管理",
      prependIcon: "mdi-airport",
      link: true,
      path: "/airport",
    },
    {
      title: "測試",
      prependIcon: "mdi-account-group",
      link: true,
      path: "/test",
    },
    {
      title: "測試TT",
      prependIcon: "mdi mdi-abacus",
      link: true,
      path: "/testtt",
    },
  ]);
  </script>
  