<template>
  <v-card class="order-card" elevation="4">
    <v-card-text>
      <div class="order-info-row">
        <span class="order-label">航線：</span>
        <span class="order-value">
          {{ order.originAirport }} → {{ order.destinationAirport }}
        </span>
      </div>
      <!-- 新增：起飛日、降落日 -->
      <div class="order-info-row">
        <span class="order-label">起飛日：</span>
        <span class="order-value">
          {{ formatDate(order.departureDate, "date") }}<br />
          {{ formatDate(order.departureDate, "time") }}
        </span>
        <span style="margin: 0 8px; color: #888">|</span>
        <span class="order-label">降落日：</span>
        <span class="order-value">
          {{ formatDate(order.arrivalDate, "date") }}<br />
          {{ formatDate(order.arrivalDate, "time") }}
        </span>
      </div>
      <div class="order-info-row">
        <span class="order-label">訂單編號：</span>
        <span class="order-value">{{ order.orderNo }}</span>
      </div>
      <div class="order-info-row">
        <span class="order-label">訂購日期：</span>
        <span class="order-value">{{ formatDate(order.bookingTime) }}</span>
      </div>
      <div class="order-info-row">
        <span class="order-label">總金額：</span>
        <span class="order-value price">{{ order.totalAmount }} 元</span>
      </div>
      <div class="order-info-row">
        <span class="order-label">累計里程：</span>
        <span class="order-value">{{ order.totalDistance }} Km</span>
      </div>
      <div class="order-info-row">
        <span class="order-label">狀態：</span>
        <span class="order-value">
          <v-chip
            :color="order.paid ? 'green' : 'red'"
            dark
            small
            class="status-chip"
          >
            <v-icon left small>{{
              order.paid ? "mdi-check-circle" : "mdi-alert-circle"
            }}</v-icon>
            {{ order.paid ? "已付款" : "未付款" }}
          </v-chip>
        </span>
      </div>
    </v-card-text>
    <v-card-actions class="order-actions">
      <v-btn
        color="primary"
        variant="elevated"
        rounded
        @click="openDetailDialog()"
      >
        <v-icon left>mdi-file-document-outline</v-icon>
        訂單詳情
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import { formatDate } from "@/utils/Date";
const props = defineProps(["order"]);
const emit = defineEmits(["open-dialog"]);



function openDetailDialog() {
  emit("open-dialog", props.order);
}
</script>

<style scoped>
.order-card {
  border-radius: 18px;
  box-shadow: 0 2px 12px rgba(33, 150, 243, 0.07);
  transition: box-shadow 0.2s;
  margin-bottom: 8px;
}
.order-card:hover {
  box-shadow: 0 8px 24px rgba(33, 150, 243, 0.14);
}
.order-info-row {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.order-label {
  font-weight: bold;
  color: #1976d2;
  min-width: 90px;
  font-size: 1rem;
}
.order-value {
  margin-left: 8px;
  font-size: 1.05rem;
  color: #333;
}
.price {
  color: #f44336;
  font-weight: bold;
}
.order-actions {
  display: flex;
  justify-content: flex-end;
  padding-right: 18px;
  padding-bottom: 12px;
}
.status-chip {
  margin-left: 0;
  font-size: 0.95rem;
}
</style>
