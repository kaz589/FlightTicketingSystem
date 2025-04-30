<template>
  <v-row
    justify="start"
    align="start"
    class="pa-6"
    style="gap: 2rem; flex-wrap: wrap">
    <div v-for="city in cities" :key="city.id" class="city-card-container">
      <CityCard :city="city" />
    </div>
  </v-row>
</template>

<script setup>
defineProps({
  cities: Array,
});
import CityCard from "./CityCardUser.vue";
</script>

<style scoped>
.city-card-container {
  width: 300px;
  min-height: 300px;
}
</style>
