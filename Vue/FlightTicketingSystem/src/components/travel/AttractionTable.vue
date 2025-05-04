<template>
  <v-data-table
    :headers="headers"
    :items="attractions"
    class="elevation-1 rounded">
    <!-- 圖片 -->
    <template #item.photoUrl="{ item }">
      <v-img
        :src="
          item.photoUrl || 'https://via.placeholder.com/96x64?text=No+Image'
        "
        height="64"
        width="96"
        cover
        class="rounded" />
    </template>

    <!-- 分類 (逗號分隔) -->
    <template #item.category="{ item }">
      {{ item.category?.join(", ") }}
    </template>

    <!-- 操作按鈕 -->
    <template #item.actions="{ item }">
      <div class="flex gap-2">
        <v-btn
          size="small"
          color="primary"
          variant="outlined"
          @click="handleEdit(item)">
          修改
        </v-btn>
        <v-btn
          size="small"
          color="error"
          variant="outlined"
          @click="handleDelete(item)">
          刪除
        </v-btn>
      </div>
    </template>
  </v-data-table>
</template>

<script setup>
const props = defineProps({
  attractions: Array,
  headers: {
    type: Array,
    default: () => [],
  },
  handleEdit: Function,
  handleDelete: Function,
});

// 這裡不用 emit，直接調用 props 傳進來的 function，跟卡片一致
</script>
