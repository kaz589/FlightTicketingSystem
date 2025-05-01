<template>
  <v-row justify="start" align="start" class="pa-6" style="row-gap: 2rem">
    <template v-if="attractions && attractions.length > 0">
      <v-col
        v-for="attraction in attractions"
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
</template>

<script setup>
import AttractionCard from "./AttractionCardAdmin.vue";

const props = defineProps({
  attractions: Array,
  cities: Array,
  handleEdit: Function,
  handleDelete: Function,
});
</script>
