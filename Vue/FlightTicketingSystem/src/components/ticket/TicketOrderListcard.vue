<template>
  <v-card class="border-xl">
    <v-card-text>
      <div>
        <label>訂單編號：{{ order.orderNo }}</label>
      </div>
      <div>
        <label>訂購時間日期：{{ order.bookingTime }}</label>
      </div>
      <div>
        <label>總金額：{{ order.totalAmount }}元</label>
      </div>
      <div>
        <label>累計里程：{{ order.totalDistance }}Km</label>
      </div>
      <div>
        <label>狀態：{{ order.paid ? '已付款' : '未付款' }}</label>
      </div>
    </v-card-text>
    <v-card-actions style="display: flex; justify-content: flex-end">
      <v-btn
        color="#F44336"
        text="訂單詳情"
        @click="openDetailDialog()"
      ></v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup>

const props = defineProps(["order"]);
const emit = defineEmits(['open-dialog']); // 這行是關鍵！
function openDetailDialog() {
  emit('open-dialog', props.order); // 將 order 傳給父元件
}
</script>

<style scoped></style>
