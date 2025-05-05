<template>
  <div>
    <v-row justify="start" align="start" class="pa-6" style="row-gap: 2rem">
      <template v-if="visibleAttractions.length > 0">
        <v-col
          v-for="attraction in visibleAttractions"
          :key="attraction.id"
          cols="12"
          sm="6"
          md="4"
          lg="3">
          <AttractionCard
            :attraction="attraction"
            :cities="cities"
            :handleEdit="handleEdit"
            :handleDelete="handleDelete" />
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
      "></div>
  </div>
</template>

<script setup>
import AttractionCard from "./AttractionCardAdmin.vue";
import { ref, computed, onMounted, nextTick } from "vue";
import { useIntersectionObserver } from "@vueuse/core";

const props = defineProps({
  attractions: Array,
  cities: Array,
  handleEdit: Function,
  handleDelete: Function,
});

const itemsPerPage = 8;
const visibleCount = ref(itemsPerPage);
const isLoading = ref(false);

const visibleAttractions = computed(() =>
  props.attractions.slice(0, visibleCount.value)
);

const loadMore = () => {
  if (visibleCount.value >= props.attractions.length) return;
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
