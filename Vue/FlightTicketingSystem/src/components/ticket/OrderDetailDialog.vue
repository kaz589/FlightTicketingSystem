<template>
  <v-dialog
    :model-value="modelValue"
    @update:model-value="emit('update:modelValue', $event)"
    max-width="800"
  >
    <v-card v-if="order">
      <v-card-title class="d-flex justify-space-between align-center">
        <div class="mx-auto" style="font-size: 3rem; font-weight: bold;">
          <span>訂單詳情</span>
        </div>
        <v-btn icon @click="closeDialog" variant="text">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>

      <v-card-text>
        <!-- 航班資訊 -->
        <div>
          <v-row
            justify="space-between"
            align="center"
            class="font-weight-bold text-h6"
          >
            <v-col class="text-center">
              <div>{{ flight.originAirport }}</div>
              <div>{{ flight.departureTime }}</div>
            </v-col>
            <v-col class="text-center">
              <div>預估里程:{{ flight.estimatedDistance }}</div>

              <v-divider>{{
                getFlightDuration(flight.departureTime, flight.arrivalTime)
              }}</v-divider>
            </v-col>
            <v-col class="text-center">
              <div>{{ flight.destinationAirport }}</div>
              <div>{{ flight.arrivalTime }}</div>
            </v-col>
          </v-row>
        </div>
        <hr />
        <!-- 訂購座位 -->
        <div>
          <h3>訂購座位</h3>
          <div
            v-for="seat in seats"
            :key="seat.seatNumber"
            style="margin-bottom: 10px"
          >
            <SeatCard :selectseat="seat" :action-type="actionType" />
          </div>
        </div>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { defineProps, defineEmits, onMounted, ref, watch } from "vue";
import SeatCard from "@/components/seats/Seatscard.vue";
import { ApiSeats, ApiFlight } from "@/utils/API";
import { getFlightDuration } from "@/utils/Date";
const props = defineProps({
  modelValue: Boolean,
  order: Object,
  actionType: String,
});
const emit = defineEmits(["update:modelValue"]);

function closeDialog() {
  emit("update:modelValue", false);
}
const seats = ref([]);
function getseats() {
  console.log(props.order);

  ApiSeats.getSeatsByTicketId(props.order.ticketId).then(
    (res) => (seats.value = res.data)
  );
}
const flight = ref({});
function getFlight() {
  console.log(props.order.flightid);

  ApiFlight.getFlightById(props.order.flightid).then((res) => {
    flight.value = res.data;
    console.log(flight.value);
  });
}

// 監聽 dialog 開啟與 order 變動
watch(
  () => [props.modelValue, props.order],
  ([visible, orderObj]) => {
    if (visible && orderObj && orderObj.ticketId) {
      getseats();
      getFlight();
    }
  }
);
</script>
