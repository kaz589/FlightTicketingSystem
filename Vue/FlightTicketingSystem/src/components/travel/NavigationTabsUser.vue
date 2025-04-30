<template>
  <!-- <div class="flex justify-center gap-6 text-base font-semibold mb-8">
    <button
      v-for="tab in tabs"
      :key="tab.name"
      @click="switchTab(tab)"
      class="relative group px-4 py-2 transition-colors duration-300"
      :class="
        selectedTab === tab.name ? 'text-black font-bold' : 'text-gray-500'
      "
    >
      <span class="flex items-center gap-1">
        <i :class="['mdi', tab.icon]"></i>
        {{ tab.name }}
      </span>
      <span
        class="absolute left-0 bottom-0 h-0.5 bg-black transition-all duration-300"
        :class="selectedTab === tab.name ? 'w-full' : 'w-0 group-hover:w-full'"
      >
      </span>
    </button>
  </div> -->

  <!-- Search Bar -->
  <!-- Flights -->
  <div
    v-show="selectedTab === '航班'"
    class="flex flex-wrap justify-center items-center gap-4 mb-10"
  >
    <div
      class="flex items-center bg-gray-100 px-4 py-3 rounded-full text-sm w-52 shadow-sm"
    >
      <span class="mr-2 mdi mdi-airplane-takeoff text-gray-500"></span>
      <strong>From:</strong>
      <input
        type="text"
        placeholder="Origin"
        class="bg-transparent outline-none ml-2 w-full placeholder:text-gray-400"
      />
    </div>

    <div
      class="flex items-center bg-gray-100 px-4 py-3 rounded-full text-sm w-52 shadow-sm"
    >
      <span class="mr-2 mdi mdi-airplane-landing text-gray-500"></span>
      <strong>To:</strong>
      <input
        v-model="travel.flightDestination"
        type="text"
        placeholder="Destination"
        class="bg-transparent outline-none ml-2 w-full placeholder:text-gray-400"
      />
    </div>

    <div
      class="flex items-center bg-gray-100 px-4 py-3 rounded-full text-sm w-64 shadow-sm"
    >
      <span class="mr-2 mdi mdi-calendar-range text-gray-500"></span>
      <input
        type="text"
        ref="dateInput"
        placeholder="Start → End"
        class="bg-transparent outline-none w-full placeholder:text-gray-400"
        readonly
      />
    </div>

    <div
      class="flex items-center bg-gray-100 px-4 py-3 rounded-full text-sm w-40 shadow-sm"
    >
      <span class="mr-2 mdi mdi-account-multiple text-gray-500"></span>
      <span>1 Traveler</span>
    </div>

    <button
      class="bg-green-500 hover:bg-green-600 text-white font-bold px-6 py-3 rounded-full transition duration-300 shadow-md"
    >
      搜尋
    </button>
  </div>

  <!-- Rewards -->
  <div
    v-show="selectedTab === '禮品'"
    class="flex justify-center items-center gap-4 mb-10"
  >
    <div
      class="flex items-center bg-gray-100 px-4 py-3 rounded-full text-sm w-full max-w-2xl shadow-sm"
    >
      <span class="mr-2 mdi mdi-gift-outline text-gray-500"></span>
      <input
        type="text"
        placeholder="請輸入禮品關鍵字..."
        class="bg-transparent outline-none w-full placeholder:text-gray-400"
      />
    </div>
    <button
      class="bg-green-500 hover:bg-green-600 text-white font-bold px-6 py-3 rounded-full transition duration-300 shadow-md"
    >
      搜尋
    </button>
  </div>

  <!-- Explore -->
  <div v-if="selectedTab === '發現更多目的地'">
    <SearchBarUser />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import flatpickr from "flatpickr";
import SearchBarUser from "./SearchBarUser.vue";
import { useTravelStore } from "@/stores/travelStore";

const dateInput = ref(null);
const travel = useTravelStore();
const selectedTab = "發現更多目的地";

const switchTab = (tab) => {
  selectedTab.value = tab.name;
  travel.resetImage();
};

const tabs = [
  { name: "航班", icon: "mdi-airplane" },
  { name: "禮品", icon: "mdi-gift" },
  { name: "發現更多目的地", icon: "mdi-map-marker-radius" },
];

onMounted(() => {
  flatpickr(dateInput.value, {
    mode: "range",
    minDate: "today",
    dateFormat: "M d",
    defaultDate: [new Date(), new Date(Date.now() + 7 * 24 * 60 * 60 * 1000)],
    onChange: (dates, str) => {
      emit("update:value", str);
    },
  });
});
</script>

<style scoped></style>
