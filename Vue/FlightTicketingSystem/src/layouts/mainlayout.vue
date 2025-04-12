<template>
  <div class="max-w-screen-xl mx-auto px-4">
    <!-- Header -->
    <header class="grid grid-cols-3 items-center py-6">
      <!-- Left: Logo -->
      <div class="flex justify-start">
        <img
          src="@/assets/Easytrip_text.png"
          alt="Easytrip Logo"
          class="h-[36px]" />
      </div>

      <!-- Middle: Navigation -->
      <nav class="flex justify-center space-x-6 text-base font-semibold">
        <a href="#" class="hover:underline">探索</a>
        <a href="#" class="hover:underline">會員</a>
        <a href="#" class="hover:underline">里程</a>
      </nav>

      <!-- Right: Language & Avatar -->
      <div class="flex justify-end items-center space-x-4 text-base">
        <span class="mdi mdi-web text-[30px]"></span>
        <span
          ><img src="https://flagcdn.com/tw.svg" width="30" alt="Taiwan"
        /></span>
        <span>TWD</span>
        <img
          src="@/assets/Easytrip.png"
          alt="User Avatar"
          class="rounded-full h-12 w-12" />
      </div>
    </header>

    <!-- Main -->
    <main class="text-center mt-10">
      <!-- Title -->
      <h1 class="text-4xl font-extrabold mb-8">易趣輕鬆飛</h1>

      <!-- Tabs -->
      <div class="flex justify-center space-x-6 text-base font-semibold mb-8">
        <div v-for="tab in tabs" :key="tab.name">
          <button
            @click="selectedTab = tab.name"
            class="flex items-center space-x-1 pb-1"
            :class="
              selectedTab === tab.name
                ? 'border-b-2 border-black'
                : 'text-gray-500'
            ">
            <!-- Icon -->
            <i :class="['mdi', tab.icon]"></i>
            <!-- Name -->
            <span>{{ tab.name }}</span>
          </button>
        </div>
      </div>

      <!-- Search Bar -->
      <div class="flex flex-wrap justify-center items-center gap-4">
        <!-- From -->
        <div
          class="flex items-center bg-gray-100 px-4 py-3 rounded-full text-sm w-52">
          <span class="mr-2 mdi mdi-airplane-takeoff"></span
          ><strong>From:</strong>
          <input
            type="text"
            placeholder="Origin"
            class="bg-transparent outline-none ml-2 w-full" />
        </div>

        <!-- To -->
        <div
          class="flex items-center bg-gray-100 px-4 py-3 rounded-full text-sm w-52">
          <span class="mr-2 mdi mdi-airplane-landing"></span
          ><strong>To:</strong>
          <input
            type="text"
            placeholder="Destination"
            class="bg-transparent outline-none ml-2 w-full" />
        </div>

        <!-- Date range (one input, two pickers) -->
        <div
          class="flex items-center bg-gray-100 px-4 py-3 rounded-full text-sm w-64">
          <span class="mr-2 mdi mdi-calendar-range"></span>
          <input
            type="text"
            ref="dateInput"
            placeholder="Start → End"
            class="bg-transparent outline-none w-full"
            readonly />
        </div>

        <!-- Travelers -->
        <div
          class="flex items-center bg-gray-100 px-4 py-3 rounded-full text-sm w-40">
          <span class="mr-2 mdi mdi-account-multiple"></span>
          <span>1 Traveler</span>
        </div>

        <!-- Search Button -->
        <button
          class="bg-green-400 hover:bg-green-500 text-white font-bold px-6 py-3 rounded-full">
          Search
        </button>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import flatpickr from "flatpickr";
import "flatpickr/dist/flatpickr.min.css";

const selectedTab = ref("Flights");

const tabs = [
  { name: "航班", icon: "mdi-airplane" },
  { name: "禮品", icon: "mdi-gift" },
  { name: "發現更多目的地", icon: "mdi-map-marker-radius" },
];

const dateInput = ref(null);

onMounted(() => {
  flatpickr(dateInput.value, {
    mode: "range",
    minDate: "today",
    dateFormat: "M d",
    defaultDate: [new Date(), new Date(Date.now() + 7 * 24 * 60 * 60 * 1000)],
    onChange: (dates, str) => {
      console.log("Selected range:", str);
    },
  });
});
</script>

<style scoped>
/* 可选：让输入光标不可见（仅用于 flatpickr 的只读场景） */
input[readonly] {
  cursor: pointer;
}
</style>
