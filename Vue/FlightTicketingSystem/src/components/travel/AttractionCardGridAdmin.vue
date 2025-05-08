<template>
  <v-row
    justify="start"
    align="start"
    class="pa-6"
    style="gap: 2rem; flex-wrap: wrap"
  >
    <div
      v-for="attraction in visibleAttractions"
      :key="attraction.id"
      class="attraction-card-container"
    >
      <AttractionCard
        :attraction="attraction"
        :cities="cities"
        :handleEdit="handleEdit"
        :handleDelete="handleDelete"
      />
    </div>
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
import { ref, computed, onMounted, nextTick, watch } from "vue";
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

const itemsPerPage = 8;
const visibleCount = ref(itemsPerPage);
const loadTrigger = ref(null);
const isLoading = ref(false);

const getPinyin = (text) =>
  pinyin(text || "", { style: pinyin.STYLE_NORMAL })
    .flat()
    .join("");

const sortedAttractions = computed(() => {
  if (!Array.isArray(props.attractions)) return [];

  const [key, order] = props.sortKey?.includes("-desc")
    ? [props.sortKey.replace("-desc", ""), "desc"]
    : [props.sortKey, "asc"];

  const getValue = (a) => {
    switch (key) {
      case "name":
        return getPinyin(a.name || "");
      case "city":
        return getPinyin(a.city || "");
      case "rating":
        return typeof a.rating === "number" ? a.rating : -1;
      default:
        return "";
    }
  };

  return [...props.attractions].sort((a, b) => {
    const aVal = getValue(a);
    const bVal = getValue(b);
    if (typeof aVal === "number" && typeof bVal === "number") {
      return order === "asc" ? aVal - bVal : bVal - aVal;
    }
    return order === "asc"
      ? aVal.localeCompare(bVal, "zh-Hant-u-co-pinyin")
      : bVal.localeCompare(aVal, "zh-Hant-u-co-pinyin");
  });
});

const visibleAttractions = computed(() =>
  sortedAttractions.value.slice(0, visibleCount.value)
);

const loadMore = () => {
  console.log("👀 正在嘗試載入更多資料...");
  if (visibleCount.value >= sortedAttractions.value.length) return;
  console.log("✅ 新增顯示筆數");
  isLoading.value = true;
  setTimeout(() => {
    visibleCount.value += itemsPerPage;
    isLoading.value = false;
  }, 300);
};

useIntersectionObserver(loadTrigger, ([{ isIntersecting }]) => {
  if (isIntersecting && !isLoading.value) {
    console.log("📦 IntersectionObserver 觸發");
    loadMore();
  }
});

onMounted(() => {
  nextTick(() => {
    const docHeight = document.documentElement.scrollHeight;
    const winHeight = window.innerHeight;
    if (docHeight <= winHeight) {
      console.log("🔄 初始頁面太短，主動加載更多");
      loadMore();
    }
  });
});
</script>

<style scoped>
.attraction-card-container {
  width: 300px;
  min-height: 300px;
}
</style>
