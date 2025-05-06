<template>
  <div>
    <v-row justify="start" align="start" class="pa-6" style="row-gap: 2rem">
      <template v-if="visibleCities.length > 0">
        <v-col
          v-for="city in visibleCities"
          :key="city.id"
          cols="12"
          sm="6"
          md="4"
          lg="3"
        >
          <CityCard
            :city="city"
            :handleEdit="handleEdit"
            :handleDelete="handleDelete"
          />
        </v-col>
      </template>

      <template v-else>
        <v-col cols="12" class="text-center py-10 text-gray-500">
          <v-icon size="48" class="mb-2">mdi-database-remove</v-icon>
          <div class="text-xl font-semibold">沒有找到符合的資料</div>
        </v-col>
      </template>
    </v-row>

    <div
      ref="loadTrigger"
      style="
        height: 80px;
        background: #eee;
        text-align: center;
        line-height: 80px;
      "
    ></div>
  </div>
</template>

<script setup>
import CityCard from "./CityCardAdmin.vue";
import { computed, ref, onMounted, nextTick } from "vue";
import { useIntersectionObserver } from "@vueuse/core";

const props = defineProps({
  cities: Array,
  sortKey: String,
  headers: { type: Array, default: () => [] },
  handleEdit: Function,
  handleDelete: Function,
});

const itemsPerPage = 8;
const visibleCount = ref(itemsPerPage);
const isLoading = ref(false);

const sortedCities = computed(() => {
  if (!props.sortKey) return props.cities;
  const [key, order] = props.sortKey.includes("-desc")
    ? [props.sortKey.replace("-desc", ""), "desc"]
    : [props.sortKey, "asc"];
  return [...props.cities].sort((a, b) => {
    const aVal = a[key] || "";
    const bVal = b[key] || "";
    return order === "asc"
      ? aVal.localeCompare(bVal, "zh-Hant-u-co-pinyin")
      : bVal.localeCompare(aVal, "zh-Hant-u-co-pinyin");
  });
});

const visibleCities = computed(() =>
  sortedCities.value.slice(0, visibleCount.value)
);

const loadMore = () => {
  if (visibleCount.value >= sortedCities.value.length) return;
  visibleCount.value += itemsPerPage;
};

const loadTrigger = ref(null);

useIntersectionObserver(loadTrigger, ([{ isIntersecting }]) => {
  if (isIntersecting && !isLoading.value) {
    loadMore();
  }
});

onMounted(() => {
  nextTick(() => {
    const docHeight = document.documentElement.scrollHeight;
    const winHeight = window.innerHeight;
    if (docHeight <= winHeight) {
      loadMore();
    }
  });
});
</script>
