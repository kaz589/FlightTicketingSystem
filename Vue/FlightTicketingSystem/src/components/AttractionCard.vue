<template>
  <div
    class="border rounded-2xl shadow-md overflow-hidden flex flex-col items-center justify-start p-3 bg-white w-[280px] min-h-[380px] mx-auto"
  >
    <div class="text-base font-semibold mb-2 text-center">
      {{ attraction.name }}
    </div>
    <div v-if="attraction.photoUrl" class="mb-2">
      <img
        :src="attraction.photoUrl"
        alt="Attraction photo"
        class="w-[260px] h-[160px] object-cover rounded-lg"
      />
    </div>
    <div class="text-center mt-2">
      <div class="flex justify-center items-center space-x-1">
        <span class="text-gray-700 text-sm"> {{ attraction.rating }} ⭐ </span>
      </div>
    </div>
    <div class="text-sm text-gray-600 text-center mb-4">
      {{ attraction.address }}
    </div>
    <div class="flex justify-center space-x-4 mt-auto mb-2">
      <button
        class="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700 text-sm"
        @click="$emit('edit', attraction)"
      >
        編輯
      </button>
      <button
        class="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700 text-sm"
        @click="$emit('show-images', attraction.id, attraction.name)"
      >
        查看所有圖片
      </button>
      <button
        class="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700 text-sm"
        @click="$emit('delete', attraction.id, attraction.name)"
      >
        刪除
      </button>
    </div>
  </div>
</template>

<script setup>
defineProps({
  attraction: Object,
});
</script>

<style scoped></style>
