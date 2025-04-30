<template>
  <div>
    <h1 class="text-4xl font-extrabold text-center mb-4">易趣輕鬆飛</h1>

    <div class="flex justify-center gap-6 text-2lg font-semibold mb-4">
      <button
        v-for="tab in tabs"
        :key="tab.name"
        @click="selectedTab = tab.name"
        class="relative group px-4 py-2 transition-colors duration-300"
        :class="
          selectedTab === tab.name ? 'text-black font-bold' : 'text-gray-500'
        "
      >
        <span class="flex items-center gap-1">
          <v-icon size="36">{{ tab.icon }}</v-icon>
          <span class="text-[25px]">{{ tab.name }}</span>
        </span>
        <span
          class="absolute left-0 bottom-0 h-0.5 bg-black transition-all duration-300"
          :class="
            selectedTab === tab.name ? 'w-full' : 'w-0 group-hover:w-full'
          "
        >
        </span>
      </button>
    </div>
    <!-- 根據選擇的 Tab 顯示不同內容 -->
    <div class="w-full mx-auto px-4">
      <div v-if="selectedTab === '航班'">
        <flightseach />
      </div>
      <div v-else-if="selectedTab === '禮品'">
        <!-- 禮品頁面內容 -->
        <test2 />
      </div>
      <div v-else-if="selectedTab === '發現更多目的地'">
        <!-- 發現更多目的地頁面內容 -->
        <userView></userView>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from "vue";
import flightseach from "@/page/member/flightseach.vue";
import test2 from "./test2.vue";
import carousel from "@/components/carousel.vue";
import userView from "@/layouts/userView.vue";
const selectedTab = ref("航班");
const tabs = [
  { name: "航班", icon: "mdi-airplane" },
  { name: "禮品", icon: "mdi-gift" },
  { name: "發現更多目的地", icon: "mdi-map-marker-radius" },
];
</script>
