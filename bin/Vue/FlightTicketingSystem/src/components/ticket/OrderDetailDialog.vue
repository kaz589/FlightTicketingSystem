<template>
    <v-dialog :model-value="modelValue" @update:model-value="emit('update:modelValue', $event)" max-width="600">
      <v-card v-if="order">
        <v-card-title>
          訂單詳情
        </v-card-title>
        <v-card-text>
          <!-- 航班資訊 -->
          <div>
            <h3>航班資訊</h3>
            <div>航班編號：{{ order }}</div>
            <div>起飛時間：{{ order }}</div>
            <div>抵達時間：{{ order }}</div>
          </div>
          <hr>
          <!-- 訂購座位 -->
          <div>
            <h3>訂購座位</h3>
            <div v-for="seat in seats" :key="seat.seatNumber" style="margin-bottom: 10px;">
            <SeatCard :selectseat="seat" />
          </div>
          </div>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" @click="closeDialog">關閉</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script setup>
  import { defineProps, defineEmits,onMounted,ref,watch } from 'vue';
  import SeatCard from '@/components/seats/Seatscard.vue';
  import{ ApiSeats}from '@/utils/API'
  const props = defineProps({
    modelValue: Boolean,
    order: Object,
    actionType: String,
  });
  const emit = defineEmits(['update:modelValue']);
  
  function closeDialog() {
    emit('update:modelValue', false);
  }
  const seats=ref([]);
  function getseats(){
console.log(props.order);

     ApiSeats.getSeatsByTicketId(props.order.ticketId).then(
         (res)=>seats.value=res.data
     )
    
  }
  // 監聽 dialog 開啟與 order 變動
watch(
  () => [props.modelValue, props.order],
  ([visible, orderObj]) => {
    if (visible && orderObj && orderObj.ticketId) {
      getseats();
    }
  }
);

  </script>