<template>
  <div>
  <div class="flight-header">
    <div v-if="flight" class="city-name">
      <h1>請確認訂購座位</h1>
    </div>
    <br />
    <div class="airport-row">
      <div class="airport-col">
        <div v-if="flight" class="city-name">
          {{ flight?.originAirport?.airportName }}
        </div>
      </div>
      <div class="plane-icon">→</div>
      <div class="airport-col">
        <div v-if="flight" class="city-name">
          {{ flight?.destinationAirport?.airportName }}
        </div>
      </div>
    </div>
    <div class="flight-info">
      <div v-if="flight" class="date">
        {{ formatDepartureTime(flight.departureTime) }}
      </div>
    </div>
  </div>
  <br />
  <hr />

  <v-container>
    <v-row align="start" justify="center" cols="1" md="1">
      <v-col
        v-for="selectseat in selectseats"
        :key="selectseat.id"
        align-self="center"
        cols="7"
        md="7"
      >
        <Seatscard :selectseat="selectseat" :action-type="actionType" />
      </v-col>
    </v-row>
  </v-container>
  <v-row justify="center">
    <v-col cols="4" class="text-right">
      <p class="text-2xl">
        總計：
        <span class="text-red-600 text-3xl font-bold"
          >${{ seatStore.totalPrice }}</span
        >
      </p>
      <p class="text-2xl">
        預計累計里程：
        <span class="text-red-600 text-3xl font-bold">
          {{ seatStore.totalDistance }} KM
        </span>
      </p>
      <v-btn
        prepend-icon="mdi mdi-cash-check"
        @click="handleSubmit"
        stacked
        size="large"
        class="text-xl py-4 px-8"
      >
        結帳</v-btn
      >
    </v-col>
  </v-row>

  <form
    ref="paymentForm"
    action="https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5"
    method="post"
  >
    <!-- 特店id固定 -->
    <input type="hidden" name="MerchantID" v-model="formData.MerchantID" />

    <!-- orderid -->
    <input
      type="hidden"
      name="MerchantTradeNo"
      v-model="formData.MerchantTradeNo"
    />

    <!-- daratime -->
    <input
      type="hidden"
      name="MerchantTradeDate"
      v-model="formData.MerchantTradeDate"
    />

    <!-- 固定aio -->
    <input type="hidden" name="PaymentType" value="aio" />

    <!-- 金額 -->
    <input type="hidden" name="TotalAmount" v-model="formData.TotalAmount" />

    <!-- 交易描述 -->
    <input type="hidden" name="TradeDesc" v-model="formData.TradeDesc" />

    <!-- 商品名稱 -->
    <input type="hidden" name="ItemName" v-model="formData.ItemName" />

    <input type="hidden" name="EncryptType" value="1" />

    <input type="hidden" name="ReturnURL" v-model="formData.ReturnURL" />
    <input
      type="hidden"
      name="ClientBackURL"
      v-model="formData.ClientBackURL"
    />
    <input
      type="hidden"
      name="OrderResultURL"
      v-model="formData.OrderResultURL"
    />

    <input type="hidden" name="ChoosePayment" value="ALL" />

    <!-- 廠商自訂參數 -->
    <input type="hidden" name="CustomField1" v-model="formData.CustomField1" />
    <input type="hidden" name="CustomField2" v-model="formData.CustomField2" />
    <input type="hidden" name="CustomField3" v-model="formData.CustomField3" />
    <input type="hidden" name="CustomField4" v-model="formData.CustomField4" />

    <input
      type="hidden"
      name="CheckMacValue"
      v-model="formData.CheckMacValue"
    />
  </form>
</div>
</template>

<script setup>
import { ref, onMounted, reactive, nextTick } from "vue";

import Seatscard from "@/components/seats/Seatscard.vue";
import { useSeatStore } from "@/stores/useSeatStore";
import {
  getCurrentDateTimeFormatted,
  generateOrderIdWithRandom,
} from "@/utils/pay";
import { formatDepartureTime } from "@/utils/Date";
import { ApiTicket } from "@/utils/API";
import { useAuthStore } from "@/stores/auth";
import Swal from "sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";
const authStore = useAuthStore();
onMounted(() => {
  // const params = new URLSearchParams(window.location.search);
  // flightId.value = params.get("flightid");
  console.log(seatStore.selectseats[0]);
  getflight();
});
const paymentForm = ref(null);
const memberid = ref(authStore.user.memberId);
const actionType = ref("cancel");
const seatStore = useSeatStore(); // 獲取 Pinia store
const selectseats = seatStore.selectseats; // 從 store 中獲取 selectseats

const formData = reactive({
  MerchantID: "2000132",
  MerchantTradeNo: "",
  MerchantTradeDate: "",
  PaymentType: "aio",
  TotalAmount: "500",
  TradeDesc: "交易描述",
  ItemName: "商品ㄧ#商品二",
  ReturnURL: "http://localhost",
  ClientBackURL: "http://localhost:8080/pay?orderid=123",
  OrderResultURL: "",
  ChoosePayment: "ALL",
  EncryptType: "1",
  CustomField1: "", // 這裡需要根據實際情況設置
  CustomField2: "",
  CustomField3: "",
  CustomField4: "",
  CheckMacValue: "",
});

const handleSubmit = async () => {
  const result = await Swal.fire({
    title: "確認要送出訂單嗎？",
    text: "送出後將進入付款流程，請確認座位及金額正確。",
    icon: "question",
    showCancelButton: true,
    confirmButtonText: "確認送出",
    cancelButtonText: "取消",
  });
  if (result.isConfirmed) {
    try {
      const host = "http://localhost:8080/pay";

      formData.ItemName = seatStore.selectedSeatNumbers;
      formData.TotalAmount = seatStore.totalPrice;
      formData.MerchantTradeDate = getCurrentDateTimeFormatted();
      formData.MerchantTradeNo = generateOrderIdWithRandom();
      formData.ClientBackURL = `${host}?orderid=${formData.MerchantTradeNo}`;

      const data = {
        orderId: formData.MerchantTradeNo, // 字串
        customerId: memberid.value, // 整數
        seatIds: seatStore.selectedSeatIds, // 整數陣列
      };
      // 等待訂票 API 回應
      const ticketRes = await ApiTicket.createTicket(data);

      const checkMacValue = await generateCheckMacValue(formData);
      if (checkMacValue) {
        formData.CheckMacValue = checkMacValue;
        console.log("CheckMacValue:", checkMacValue);
        await nextTick(); // 等待 DOM 更新

        if (paymentForm.value) {
          console.log(paymentForm.value);

          paymentForm.value.submit();
        }
      } else {
        console.error("CheckMacValue is null");
      }
    } catch (error) {
      console.error("Error:", error);
    }
  }
};

async function generateCheckMacValue(params) {
  try {
    const { checkMacValue, ...restParams } = params; // 移除 checkMacValue
    const res = await ApiTicket.getCheckMacValue(restParams);
    return res.data;
  } catch (error) {
    console.error("Error generating CheckMacValue:", error);
    return null;
  }
}
const flight = ref([]);
function getflight() {
  flight.value = seatStore.selectseats[0].flight;
}
</script>

<style scoped>
/* 飛機圖示 */
.plane-icon {
  font-size: 2.2rem;
  color: #1976d2;
}

/* 標頭區塊 */
.flight-header {
  margin: 20px 0;
  text-align: center;
}

/* 城市名稱 */
.city-name {
  font-size: 2.5rem;
  font-weight: bold;
  letter-spacing: 2px;
}

/* 標籤文字 */
.label {
  font-size: 1rem;
  color: #888;
  margin-bottom: 4px;
}

/* 航班資訊 */
.flight-info {
  margin-top: 18px;
}

/* 日期文字 */
.date {
  font-size: 1.15rem;
  font-weight: 600;
}

/* 城市行（如需用到） */
.city-row {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
}

/* 機場行 */
.airport-row {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 40px;
}

/* 機場欄 */
.airport-col {
  display: flex;
  flex-direction: column;
  align-items: center;
}

/* 分隔線與時間區塊 */
.divider-center {
  display: flex;
  align-items: center;
  gap: 8px;
}
.divider-line {
  width: 90px;
  height: 2px;
  background: #bdbdbd;
  border-radius: 1px;
}
.divider-time {
  font-size: 1.1rem;
  color: #888;
  padding: 0 4px;
}
</style>
