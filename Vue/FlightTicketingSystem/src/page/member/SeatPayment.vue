<template>
    <div>
這是結帳頁面
    </div>

    <v-container   >  
    <v-row align="start" justify="center"  cols="1" md="1">
      <v-col
        v-for="selectseat in selectseats"
        :key="selectseat.id"
        align-self="center"
        cols="7"
        md="7"
      >
      <Seatscard :selectseat="selectseat"/>
      </v-col>
    </v-row>
  </v-container>
  <v-row justify="center">
    <v-col
       
        cols="4"
      >
      <p>總計： <span class="text-red-600 text-xl font-bold">${{seatStore.totalPrice}}</span></p>
      <v-btn prepend-icon="$vuetify" @click="generateOrderId()" stacked> Button</v-btn>
      <v-btn @click=" generateCheckMacValue(formData, hashKey, hashIV)">TEST1</v-btn>
      <v-btn @click=" generateCheckMacValue2(formData)">TEST2</v-btn>
      </v-col>
    </v-row>


    <form ref="paymentForm" action="https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5" method="post">
    <!-- 特店id固定 -->
    <input type="hidden" name="MerchantID" v-model="formData.MerchantID" />

    <!-- orderid -->
    <input type="hidden" name="MerchantTradeNo" v-model="formData.MerchantTradeNo" />

    <!-- daratime -->
    <input type="hidden" name="MerchantTradeDate" v-model="formData.MerchantTradeDate" />

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
    <input type="hidden" name="ClientBackURL" v-model="formData.ClientBackURL" />
    <input type="hidden" name="OrderResultURL" v-model="formData.OrderResultURL" />

    <input type="hidden" name="ChoosePayment" value="ALL" />

    <!-- 廠商自訂參數 -->
    <input type="hidden" name="CustomField1" v-model="formData.CustomField1" />
    <input type="hidden" name="CustomField2" v-model="formData.CustomField2" />
    <input type="hidden" name="CustomField3" v-model="formData.CustomField3" />
    <input type="hidden" name="CustomField4" v-model="formData.CustomField4" />

    <input  type="hidden"  name="CheckMacValue" v-model="formData.CheckMacValue" />

    <v-btn type="button" @click="handleSubmit" >確認</v-btn>
  </form>
</template>

<script setup>
import { ref, onMounted,reactive,nextTick } from "vue";

import Seatscard from "@/components/seats/Seatscard.vue";
import { useSeatStore } from '@/stores/useSeatStore';
import {generateOrderId ,getCurrentDateTimeFormatted,generateOrderIdWithRandom}from'@/utils/pay';

import { ApiTicket}from "@/utils/API";

onMounted(() => {
  const params = new URLSearchParams(window.location.search);
  flightId.value = params.get("flightid");
})
const paymentForm = ref(null);



const seatStore = useSeatStore(); // 獲取 Pinia store
const selectseats = seatStore.selectseats; // 從 store 中獲取 selectseats

const formData = reactive({
      MerchantID : '2000132',
      MerchantTradeNo : '',
      MerchantTradeDate : '',
      PaymentType: "aio",
      TotalAmount: "500",
      TradeDesc : '交易描述',
      ItemName : '商品ㄧ#商品二',
      ReturnURL: "http://localhost:8080/pay",
      ClientBackURL: "http://localhost:8080",
      OrderResultURL: "http://localhost:8080/pay",
      ChoosePayment : "ALL",
      EncryptType: "1",
      CustomField1: '', // 這裡需要根據實際情況設置
      CustomField2: '',
      CustomField3: '',
      CustomField4: '',
      CheckMacValue:''
    });

    const handleSubmit = async () => {
  try {
    formData.ItemName=seatStore.selectedSeatNumbers
    formData.TotalAmount = seatStore.totalPrice;
    formData.MerchantTradeDate=getCurrentDateTimeFormatted();
    formData.MerchantTradeNo =generateOrderIdWithRandom();
    const checkMacValue = await generateCheckMacValue(formData);
    if (checkMacValue) {
      formData.CheckMacValue  = checkMacValue;
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


</script>

<style  scoped>

</style>