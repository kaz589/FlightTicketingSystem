<template>
  <v-row
    justify="start"
    align="start"
    class="pa-6"
    style="gap: 2rem; flex-wrap: wrap"
  >
    <div v-for="item in items" :key="item.id" class="card-wrapper">
      <component
        :is="cardComponent"
        v-bind="{ ...sharedProps, [itemPropName]: item }"
        v-on="listeners"
      />
    </div>
  </v-row>
</template>

<script setup>
/**
 * 通用 Card Grid 元件
 * @props items - 要渲染的物件陣列（例如城市、景點等）
 * @props cardComponent - 要用的卡片元件（例如 CityCardUser）
 * @props itemPropName - 傳入卡片元件的 prop 名稱（例如 "city"）
 * @props sharedProps - 要一起傳給每張卡片的其他 props（例如 imageBaseUrl）
 * @props listeners - 要掛載在每張卡片上的事件監聽器
 */

defineProps({
  items: Array,
  cardComponent: Object,
  itemPropName: {
    type: String,
    default: "item",
  },
  sharedProps: {
    type: Object,
    default: () => ({}),
  },
  listeners: {
    type: Object,
    default: () => ({}),
  },
});
</script>

<style scoped>
.card-wrapper {
  width: 300px;
  min-height: 300px;
}
</style>
