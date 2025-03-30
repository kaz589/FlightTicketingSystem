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
  
      <v-app-bar border="b" class="ps-4" flat>
        <v-app-bar-nav-icon
          v-if="$vuetify.display.smAndDown"
          @click="drawer = !drawer"
        />
  
        <v-app-bar-title>Application</v-app-bar-title>
  
        <template #append>
          <v-btn class="text-none me-2" height="48" icon slim>
            <v-avatar
              color="surface-light"
              image="https://cdn.vuetifyjs.com/images/john.png"
              size="32"
            />
  
            <v-menu activator="parent">
              <v-list density="compact" nav>
                <v-list-item
                  append-icon="mdi-cog-outline"
                  link
                  title="Settings"
                />
  
                <v-list-item append-icon="mdi-logout" link title="Logout" />
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
      prependIcon: "mdi-account-group",
      link: true,
      path: "/members",
    },
  ]);
  </script>
  