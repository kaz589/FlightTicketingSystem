<template>
  <v-row justify="start" align="start" class="pa-6" style="row-gap: 2rem">
    <template v-if="cities && cities.length > 0">
      <v-col
        v-for="city in cities"
        :key="city.id"
        cols="12"
        sm="6"
        md="4"
        lg="3">
        <CityCard
          :city="city"
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
</template>

<script setup>
import CityCard from "./CityCardAdmin.vue";

const props = defineProps({
  cities: Array,
  headers: { type: Array, default: () => [] },
  handleEdit: Function,
  handleDelete: Function,
});
</script>
