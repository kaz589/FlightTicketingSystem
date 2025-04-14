<template>
  <div class="flex justify-center mt-6">
    <div
      class="flex w-full max-w-xl shadow-md rounded-full border border-gray-200 overflow-hidden">
      <input
        v-model="searchQuery"
        type="text"
        placeholder="請輸入關鍵字"
        class="w-full px-6 py-3 focus:outline-none" />
      <button
        class="bg-emerald-400 text-white px-6 py-3 font-semibold hover:bg-emerald-500"
        @click="handleSearch">
        Search
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";

const props = defineProps({
  selectedTab: String,
});

const emit = defineEmits(["search-city", "search-attraction"]);

const searchQuery = ref("");

const handleSearch = () => {
  if (props.selectedTab === "cities") {
    emit("search-city", searchQuery.value);
  } else if (props.selectedTab === "attractions") {
    emit("search-attraction", searchQuery.value);
  }
};
</script>
