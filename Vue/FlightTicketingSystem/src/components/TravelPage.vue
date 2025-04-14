<template>
  <div class="min-h-screen bg-gray-50 text-black p-6">
    <TabNavigation :selectedTab="selectedTab" @select-tab="setSelectedTab" />
    <SearchBar v-model="searchQuery" @search="search" />
    <div class="mt-6 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <CityCard
        v-if="selectedTab === 'cities'"
        v-for="city in results"
        :key="city.id"
        :city="city"
        @edit="(c) => console.log('edit', c)"
        @delete="(id) => console.log('delete', id)"
      />
      <AttractionCard
        v-if="selectedTab === 'attractions'"
        v-for="attr in results"
        :key="attr.id"
        :attraction="attr"
        @edit="(a) => console.log('edit', a)"
        @delete="(id) => console.log('delete', id)"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import TabNavigation from "./TabNavigation.vue";
import SearchBar from "./SearchBar.vue";
import CityCard from "./CityCard.vue";
import AttractionCard from "./AttractionCard.vue";
import axios from "axios";

const selectedTab = ref("cities");

const setSelectedTab = (tab) => {
  selectedTab.value = tab;
  results.value = [];
};

const searchQuery = ref("");
const results = ref([]);

const apiPaths = {
  cities: "http://localhost:8080/cities/city",
  attractions: "http://localhost:8080/attractions",
};

const search = async () => {
  try {
    const base = apiPaths[selectedTab.value];
    const query = searchQuery.value?.trim();
    if (!query) return;
    const url = `${base}/${encodeURIComponent(query)}`;
    const response = await axios.get(url);

    if (selectedTab.value === "attractions") {
      const attractions = await Promise.all(
        response.data.map(async (attr) => {
          try {
            const photoRes = await axios.get(
              `http://localhost:8080/photos/attraction/${attr.id}`
            );
            const relativeUrl = photoRes.data[0]?.url || "";
            attr.photoUrl = relativeUrl
              ? `http://localhost:8080/${relativeUrl}`
              : "";
          } catch {
            attr.photoUrl = "";
          }
          return attr;
        })
      );
      results.value = attractions;
    } else {
      results.value = response.data;
    }
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};
</script>
