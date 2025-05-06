<template>
  <v-row
    justify="start"
    align="start"
    class="pa-6"
    style="gap: 2rem; flex-wrap: wrap"
  >
    <div
      v-for="city in visibleCities"
      :key="city.id"
      class="city-card-container"
    >
      <CityCard :city="city" />
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
import { ref, computed, onMounted, nextTick } from "vue";
import { useIntersectionObserver } from "@vueuse/core";
import CityCard from "./CityCardUser.vue";

const props = defineProps({
  cities: Array,
});

const itemsPerPage = 10;
const visibleCount = ref(itemsPerPage);
const loadTrigger = ref(null);
const isLoading = ref(false);

const visibleCities = computed(() => props.cities.slice(0, visibleCount.value));

const loadMore = () => {
  if (visibleCount.value >= props.cities.length) return;
  isLoading.value = true;
  setTimeout(() => {
    visibleCount.value += itemsPerPage;
    isLoading.value = false;
  }, 300);
};

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

<style scoped>
.city-card-container {
  width: 300px;
  min-height: 300px;
}
</style>
