<template>
  <div class="text-black p-6">
    <h1 class="text-4xl font-bold text-center mb-6">請選擇操作</h1>

    <div class="flex justify-center space-x-6 text-gray-700 mb-4">
      <div :class="tabClass('cities')" @click="$emit('select-tab', 'cities')">
        查詢城市
      </div>
      <div
        :class="tabClass('attractions')"
        @click="$emit('select-tab', 'attractions')"
      >
        查詢景點
      </div>
      <div
        :class="tabClass('allCities')"
        @click="$emit('select-tab', 'allCities')"
      >
        全部城市
      </div>
      <div
        :class="tabClass('allAttractions')"
        @click="$emit('select-tab', 'allAttractions')"
      >
        全部景點
      </div>
      <div
        :class="tabClass('addCities')"
        @click="$emit('select-tab', 'addCities')"
      >
        增加城市
      </div>
      <div
        :class="tabClass('addAttractions')"
        @click="$emit('select-tab', 'addAttractions')"
      >
        增加景點
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  selectedTab: String,
});

const tabClass = (tabName) => [
  "cursor-pointer",
  props.selectedTab === tabName
    ? "border-b-2 border-black pb-1 font-semibold"
    : "",
];
</script>
