<template>
  <v-card
    class="mx-auto transition-all duration-300 hover:shadow-xl hover:scale-[1.02]"
    max-width="320"
    elevation="3"
    rounded="xl">
    <v-carousel
      v-if="photos.length > 0"
      height="180"
      hide-delimiter-background
      delimiter-icon="mdi-circle"
      show-arrows="hover"
      cycle
      class="rounded-t-xl">
      <v-carousel-item v-for="(photo, i) in photos" :key="i">
        <v-img :src="photo.url" height="180" cover />
      </v-carousel-item>
    </v-carousel>

    <v-card-title class="text-center text-lg font-semibold py-2">
      {{ attraction.name }}
    </v-card-title>
    <v-card-subtitle class="text-center pb-2">
      {{ attraction.address }}
    </v-card-subtitle>
    <v-card-text class="text-center">
      <v-rating
        :model-value="attraction.rating"
        color="yellow-darken-3"
        dense
        readonly
        half-increments
        size="18" />
      <div class="text-caption text-grey mt-1">
        {{ attraction.category?.join(", ") }}
      </div>
    </v-card-text>

    <v-card-actions class="justify-center pb-4">
      <v-btn
        color="primary"
        variant="outlined"
        size="small"
        @click="handleEdit(attraction)">
        修改
      </v-btn>
      <v-btn
        color="error"
        variant="outlined"
        size="small"
        class="ml-2"
        @click="handleDelete(attraction)">
        刪除
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup>
import { ref, onMounted } from "vue";

const { attraction, handleEdit, handleDelete } = defineProps({
  attraction: Object,
  handleEdit: Function,
  handleDelete: Function,
});

const photos = ref([]);

onMounted(async () => {
  try {
    const res = await fetch(
      `http://localhost:8080/photos/attraction/${attraction.id}`
    );
    const data = await res.json();
    photos.value = data.map((p) => ({
      ...p,
      url: p.url.startsWith("http") ? p.url : `http://localhost:8080${p.url}`,
    }));
  } catch (err) {
    console.error("載入圖片失敗", err);
  }
});
</script>
