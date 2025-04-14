<template>
  <div class="flex justify-center gap-2">
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
</template>

<script setup>
defineProps({
  attraction: Object,
});
defineEmits(["edit", "show-images", "delete"]);
</script>
