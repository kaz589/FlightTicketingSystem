<template>
  <v-data-table :headers="headers" :items="cities" class="elevation-1 rounded">
    <template #item.actions="{ item }">
      <v-btn
        size="small"
        color="primary"
        variant="outlined"
        @click="handleEdit(item)"
      >
        修改
      </v-btn>
      <v-btn
        size="small"
        color="error"
        variant="outlined"
        class="ml-2"
        @click="handleDelete(item)"
      >
        刪除
      </v-btn>
    </template>
  </v-data-table>
</template>

<script setup>
const props = defineProps({
  cities: Array,
  headers: {
    type: Array,
    default: () => [],
  },
  handleEdit: Function,
  handleDelete: Function,
});
</script>
