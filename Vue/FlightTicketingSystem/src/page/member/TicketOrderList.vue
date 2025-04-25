<template>
  <div></div>
  <v-container>
    <div class="order-list-header">
      <h1>訂單列表</h1>
    </div>

    <v-row align="start" justify="center" cols="1" md="1">
      <v-col
        v-for="order in orders"
        :key="order.id"
        align-self="center"
        cols="7"
        md="7"
      >
        <TicketOrderListcard :order="order" @open-dialog="showDialog" />
      </v-col>
    </v-row>
    <OrderDetailDialog v-model="dialogVisible" :order="selectedOrder"  :action-type="''"/>
  </v-container>
</template>

<script setup>
import TicketOrderListcard from "@/components/ticket/TicketOrderListcard.vue";
import OrderDetailDialog from "@/components/ticket/OrderDetailDialog.vue";
import { ref, onMounted } from "vue";
import { ApiTicket } from "@/utils/API";
import { useAuthStore } from "@/stores/auth";
// Pinia store 實例
const authStore = useAuthStore();
onMounted(() => {
  ApiTicket.getTicketsByCustomerId(authStore.user.memberId).then((response) => {
    console.log(response.data);

    orders.value = response.data;
  });
});
const orders = ref([]);
const dialogVisible = ref(false);
const selectedOrder = ref(null);

function showDialog(order) {
  selectedOrder.value = order;
  dialogVisible.value = true;
}
</script>

<style scoped>
.order-list-header {
  text-align: center;
  margin-bottom: 32px;
}
.order-list-header h1 {
  font-weight: bold;
  font-size: 2.2rem;
  display: inline-block;
  padding-bottom: 8px;
  color: black;
  letter-spacing: 2px;
}</style>
