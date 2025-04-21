<template>
  <div>
    <!-- 搜尋列 -->
    <div class="flex justify-center items-center gap-4 mb-10">
      <div
        class="flex items-center bg-gray-100 px-4 py-3 rounded-full text-sm w-full max-w-2xl shadow-sm"
      >
        <span class="mr-2 mdi mdi-map-marker-outline text-gray-500"></span>
        <input
          v-model="model"
          type="text"
          placeholder="請輸入城市、景點或地區名稱..."
          class="bg-transparent outline-none w-full placeholder:text-gray-400"
        />
      </div>
      <button
        @click="handleSearch"
        class="bg-green-500 hover:bg-green-600 text-white font-bold px-6 py-3 rounded-full transition duration-300 shadow-md"
      >
        搜尋
      </button>
    </div>

    <!-- Loading Spinner -->
    <div v-if="loading" class="flex justify-center my-8">
      <v-progress-circular indeterminate color="green" size="40" />
    </div>

    <!-- 搜尋結果 -->
    <div
      v-if="
        hasSearched &&
        !loading &&
        (cityResults.length > 0 || attractionResults.length > 0)
      "
      class="px-4"
    >
      <div v-if="cityResults.length > 0" class="max-w-7xl mx-auto px-6">
        <h2 class="text-xl font-bold mb-4">相關城市</h2>
        <CardGrid :cities="cityResults" />
      </div>

      <div
        v-if="attractionResults.length > 0"
        class="mt-10 max-w-7xl mx-auto px-6"
      >
        <h2 class="text-xl font-bold mb-4">相關景點</h2>
        <AttractionCardGrid
          :attractions="attractionResults"
          :cities="allCities"
        />
      </div>
    </div>

    <!-- 無結果提示 -->
    <div
      v-if="
        hasSearched &&
        !loading &&
        model &&
        cityResults.length === 0 &&
        attractionResults.length === 0
      "
      class="text-center text-gray-500 mt-10"
    >
      查無符合的城市或景點
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";



const model = defineModel();
const loading = ref(false);
const hasSearched = ref(false);
const cityResults = ref([]);
const attractionResults = ref([]);
const allCities = ref([]);

const cityStore = useCityStore();
const attractionStore = useAttractionStore();

const handleSearch = async () => {
  const query = model.value?.trim();
  if (!query) {
    cityResults.value = [];
    attractionResults.value = [];
    hasSearched.value = false;
    return;
  }

  cityResults.value = [];
  attractionResults.value = [];
  loading.value = true;
  hasSearched.value = true;

  try {
    const [cityRes, attractionRes] = await Promise.all([
      cityStore.searchByName(query),
      attractionStore.searchByName(query),
    ]);
    cityResults.value = cityRes;
    attractionResults.value = attractionRes;
  } catch (e) {
    console.error("搜尋失敗", e);
  } finally {
    loading.value = false;
  }
};

onMounted(async () => {
  await cityStore.fetchCities();
  allCities.value = cityStore.cities;
});
</script>
