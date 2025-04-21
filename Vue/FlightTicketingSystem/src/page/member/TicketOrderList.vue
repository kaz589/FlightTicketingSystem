<template>
    <div>
<h1>
這是訂單列表
</h1>
    </div>
    <v-container   >  
    <v-row align="start" justify="center"  cols="1" md="1">
      <v-col
        v-for="order in orders"
        :key="order.id"
        align-self="center"
        cols="7"
        md="7"
      >
      <TicketOrderListcard :order="order" @open-dialog="showDialog"/>
      </v-col>
    </v-row>
    <OrderDetailDialog
      v-model="dialogVisible"
      :order="selectedOrder"
    />
  </v-container>
 
</template>

<script setup>
import TicketOrderListcard from '@/components/ticket/TicketOrderListcard.vue';
import OrderDetailDialog from '@/components/ticket/OrderDetailDialog.vue';
import { ref ,onMounted} from 'vue';
import{ ApiTicket }from'@/utils/API';

onMounted(() => {
    ApiTicket.getTicketsByCustomerId(1).then((response)=>{
      console.log(response.data);
      
        orders.value = response.data;
    })
})
const orders = ref([]);
const dialogVisible = ref(false);
const selectedOrder = ref(null)

function showDialog(order) {
  selectedOrder.value = order;
  dialogVisible.value = true;
}

</script>

<style  scoped>

</style>