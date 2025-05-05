<template>
  <v-row justify="start" align="start" class="pa-6" style="row-gap: 2rem">
    <template v-if="visibleAttractions.length">
      <v-col
        v-for="attraction in visibleAttractions"
        :key="attraction.id"
        cols="12"
        sm="6"
        md="4"
        lg="3"
      >
        <AttractionCard
          :attraction="attraction"
          :cities="cities"
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
  />
</template>

<script setup>
import { ref, computed, onMounted, nextTick } from "vue";
import { useIntersectionObserver } from "@vueuse/core";
import AttractionCard from "./AttractionCardUser.vue";
import pinyin from "pinyin";

const props = defineProps({
  attractions: Array,
  cities: Array,
  sortKey: String,
  handleEdit: Function,
  handleDelete: Function,
});

const getPinyin = (text) =>
  pinyin(text || "", { style: pinyin.STYLE_NORMAL })
    .flat()
    .join("");

const getCityName = (cityId) =>
  props.cities.find((c) => c.id === cityId)?.name || "";

const sortedAttractions = computed(() => {
  if (!Array.isArray(props.attractions)) return [];

  const copy = [...props.attractions];

  const [key, order] = props.sortKey?.includes("-desc")
    ? [props.sortKey.replace("-desc", ""), "desc"]
    : [props.sortKey, "asc"];

  const getValue = (a) => {
    let value;
    switch (key) {
      case "name":
        value = getPinyin(a.name || "");
        return value;
      case "city":
        value = getPinyin(a.city || "");
        return value;
      case "rating":
        return typeof a.rating === "number" ? a.rating : -1;
      default:
        return "";
    }
  };

  return copy.sort((a, b) => {
    const aVal = getValue(a);
    const bVal = getValue(b);

    if (typeof aVal === "number" && typeof bVal === "number") {
      return order === "asc" ? aVal - bVal : bVal - aVal;
    }

    return order === "asc"
      ? aVal.localeCompare(bVal)
      : bVal.localeCompare(aVal);
  });
});

const itemsPerPage = 8;
const visibleCount = ref(itemsPerPage);
const isLoading = ref(false);

const visibleAttractions = computed(() =>
  sortedAttractions.value.slice(0, visibleCount.value)
);

const loadMore = () => {
  if (visibleCount.value >= sortedAttractions.value.length) return;
  isLoading.value = true;
  setTimeout(() => {
    visibleCount.value += itemsPerPage;
    isLoading.value = false;
  }, 500);
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
