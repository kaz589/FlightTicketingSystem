<template>
  <div class="min-h-screen bg-gray-50 text-black p-6">
    <TabNavigation :selectedTab="selectedTab" @select-tab="setSelectedTab" />
    <SearchBar
      :selectedTab="selectedTab"
      @search-city="searchCity"
      @search-attraction="searchAttraction" />
  </div>
</template>

<script setup>
import { ref } from "vue";
import TabNavigation from "./TabNavigation.vue";
import SearchBar from "./SearchBar.vue";

const selectedTab = ref("cities");

const setSelectedTab = (tab) => {
  selectedTab.value = tab;
};

const searchCity = (query) => {
  console.log("搜尋城市：", query);
  // 呼叫搜尋城市 API 或過濾資料
};

const searchAttraction = (query) => {
  console.log("搜尋景點：", query);
  // 呼叫搜尋景點 API 或過濾資料
};
</script>
