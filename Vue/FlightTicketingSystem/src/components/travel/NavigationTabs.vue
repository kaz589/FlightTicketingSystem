<template>
  <div class="text-center my-6">
    <h1 class="text-4xl font-bold mb-4">請選擇操作</h1>

    <div v-if="modelValue === 'cities'">
      <p class="text-lg"><v-icon class="mb-2">mdi-city</v-icon>查詢城市</p>
    </div>

    <div v-else-if="modelValue === 'addCities'">
      <p class="text-lg"><v-icon class="mb-2">mdi-plus-box</v-icon>增加城市</p>
    </div>

    <div v-else-if="modelValue === 'allCities'">
      <p class="text-lg"><v-icon class="mb-2">mdi-domain</v-icon>全部城市</p>
    </div>

    <div v-else-if="modelValue === 'attractions'">
      <p class="text-lg">
        <v-icon class="mb-2">mdi-map-marker</v-icon>查詢景點
      </p>
    </div>

    <div v-else-if="modelValue === 'addAttractions'">
      <p class="text-lg">
        <v-icon class="mb-2">mdi-plus-circle</v-icon>增加景點
      </p>
    </div>

    <div v-else-if="modelValue === 'allAttractions'">
      <p class="text-lg">
        <v-icon class="mb-2">mdi-image-filter-hdr</v-icon>全部景點
      </p>
    </div>
  </div>

  <div class="flex justify-center gap-4 mb-6">
    <div class="dropdown-wrapper">
      <v-select
        v-model="selectedAction"
        :items="actions[selectedCategory]"
        item-title="label"
        item-value="value"
        label="選擇操作"
        :prepend-inner-icon="
          actionIcons[selectedCategory][selectedAction] || 'mdi-cog'
        "
        dense
        outlined />
    </div>
    <div class="dropdown-wrapper">
      <v-select
        v-model="selectedCategory"
        :items="categories"
        item-title="label"
        item-value="value"
        label="選擇類別"
        prepend-inner-icon="mdi-menu"
        dense
        outlined />
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";

const modelValue = defineModel("selectedTab");

const selectedCategory = ref("cities");
const selectedAction = ref("query");

const categories = [
  { label: "城市", value: "cities" },
  { label: "景點", value: "attractions" },
];

const actions = {
  cities: [
    { label: "查詢", value: "query" },
    { label: "增加", value: "add" },
    { label: "全部", value: "all" },
  ],
  attractions: [
    { label: "查詢", value: "query" },
    { label: "增加", value: "add" },
    { label: "全部", value: "all" },
  ],
};

const actionIcons = {
  cities: {
    query: "mdi mdi-playlist-edit",
    add: "mdi mdi-playlist-edit",
    all: "mdi mdi-playlist-edit",
  },
  attractions: {
    query: "mdi mdi-playlist-edit",
    add: "mdi mdi-playlist-edit",
    all: "mdi mdi-playlist-edit",
  },
};

const actionMap = {
  cities: {
    query: "cities",
    add: "addCities",
    all: "allCities",
  },
  attractions: {
    query: "attractions",
    add: "addAttractions",
    all: "allAttractions",
  },
};

watch([selectedCategory, selectedAction], () => {
  modelValue.value = actionMap[selectedCategory.value][selectedAction.value];
});
</script>

<style>
.dropdown-container {
  justify-content: center;
}

.dropdown-wrapper {
  max-width: 360px;
  width: 100%;
}
</style>
