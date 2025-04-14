<template>
  <div
    class="border rounded-2xl shadow-md overflow-hidden flex flex-col items-center justify-start p-3 bg-white w-[280px] min-h-[300px] mx-auto">
    <img
      :src="`http://localhost:8080${city.image}`"
      :alt="city.name"
      class="w-[260px] h-[160px] object-cover rounded-lg mb-4" />
    <div class="text-base font-semibold text-center mb-4">
      <div>{{ city.name }}</div>
      <div>{{ city.country }}</div>
    </div>
    <div class="flex space-x-3 mt-auto">
      <button
        class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-1 rounded text-sm"
        @click="$emit('edit', city)">
        修改
      </button>
      <button
        class="bg-red-600 hover:bg-red-700 text-white px-4 py-1 rounded text-sm"
        @click="$emit('delete', city.id)">
        刪除
      </button>
    </div>
  </div>
</template>

<script setup>
defineProps({
  city: Object,
});
</script>

<style scoped></style>
