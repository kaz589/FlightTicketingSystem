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


  <form action="https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5" method="post">
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

  <input  name="CheckMacValue" v-model="formData.CheckMacValue" />

  <v-btn type="submit">確認</v-btn>
</form>
</template>

<script setup>
import { ref, computed, onMounted,reactive } from "vue";
import { ApiSeats } from "@/utils/API";
import Seatscard from "@/components/seats/Seatscard.vue";
import { useSeatStore } from '@/stores/useSeatStore';
import {generateOrderId ,getCurrentDateTimeFormatted,generateOrderIdWithRandom}from'@/utils/pay';
import axios from 'axios';
import { ApiTicket}from "@/utils/API";
let hashKey = "5294y06JbISpM5x9";
let hashIV = "v77hoKGq4kWxNNIS";
onMounted(() => {

  formData.MerchantTradeDate=getCurrentDateTimeFormatted();
  formData.MerchantTradeNo =generateOrderIdWithRandom();

 // formData.checkMacValue= generateCheckMacValue(formData);
 
 //formData.CheckMacValue=  generateCheckMacValue(formData, hashKey, hashIV);
//formData.CheckMacValue=  generateCheckMacValue2(formData);
generateCheckMacValue2(formData)
      .then(checkMacValue => {
          formData.CheckMacValue = checkMacValue;
          console.log(formData.CheckMacValue);
          console.log(formData);
      })
      .catch(error => {
          console.error("Error:", error);
      });
  
  
})
const seatStore = useSeatStore(); // 獲取 Pinia store
const selectseats = seatStore.selectseats; // 從 store 中獲取 selectseats

const formData = reactive({
    MerchantID : '2000132',
    MerchantTradeNo : '',
    MerchantTradeDate : '',
    PaymentType: "aio",
    TotalAmount: "500",
    TradeDesc : '交易描述',
    ItemName : '123,123',
    ReturnURL: "http://localhost:8080/pay",
    ClientBackURL: "http://localhost:8080",
    OrderResultURL: "http://localhost:8080/pay",
    ChoosePayment : "ALL",
    EncryptType: "1",
    CustomField1: '', // 這裡需要根據實際情況設置
    CustomField2: '',
    CustomField3: '',
    CustomField4: '',
  });
// 提取 seatNumber 列表
function getSeatNumbers(selectseats) {
  return selectseats.map(selectseats => selectseats.seatNumber);
}

// 使用範例
const seatNumbers = getSeatNumbers(selectseats);

const submitForm = () => {
    const params = new URLSearchParams();
    for (const key in formData) {
      params.append(key, formData[key]);
    }

    axios.post('https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5', params, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
    .then(response => {
      console.log('Response:', response.data);
      // 在這裡處理成功的響應
    })
    .catch(error => {
      console.error('Error:', error);
      // 在這裡處理錯誤
    });
  };
  async function generateCheckMacValue2(params) {
  try {
    const { checkMacValue, ...restParams } = params; // 移除 checkMacValue
      const res = await ApiTicket.getCheckMacValue(restParams);
      console.log(res.data);
      return res.data;
  } catch (error) {
      console.error("Error generating CheckMacValue:", error);
      return null;
  }
}

function generateCheckMacValue(params, hashKey, hashIV) {
const { checkMacValue, ...restParams } = params; // 移除 checkMacValue


    let sortedParams = Object.keys(restParams)
      .sort()
      .reduce((obj, key) => {
        obj[key] = params[key];
        return obj;
      }, {});

    let encodedParams = Object.keys(sortedParams)
      .map((key) => `${key}=${sortedParams[key]}`)
      .join("&");
    let preHash = `HashKey=${hashKey}&${encodedParams}&HashIV=${hashIV}`;
    let encodedPreHash = encodeURIComponent(preHash)
      .toLowerCase()
      .replace(/%20/g, "+")
      .replace(/%2d/g, "-")
      .replace(/%5f/g, "_")
      .replace(/%2e/g, ".")
      .replace(/%21/g, "!")
      .replace(/%2a/g, "*")
      .replace(/%28/g, "(")
      .replace(/%29/g, ")");
    let sha256Hash = CryptoJS.SHA256(encodedPreHash)
      .toString()
      .toUpperCase();
      console.log(sha256Hash);
      
    return sha256Hash;
  }
</script>

<style  scoped>

</style>