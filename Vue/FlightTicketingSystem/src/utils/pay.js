import { ApiTicket}from "@/utils/API";
import axios from "axios";



export function generateOrderIdWithRandom() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, "0");
    const day = String(now.getDate()).padStart(2, "0");
    const hour = String(now.getHours()).padStart(2, "0");
    const minute = String(now.getMinutes()).padStart(2, "0");
    const second = String(now.getSeconds()).padStart(2, "0");
    const millisecond = String(now.getMilliseconds()).padStart(3, "0");
    const random = Math.floor(Math.random() * 1000); // 產生 0-999 的隨機數

    return `${year}${month}${day}${hour}${minute}${second}${millisecond}${String(
      random
    ).padStart(3, "0")}`;
  }
export  function getCurrentDateTimeFormatted() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, "0"); // 月份從 0 開始，需要 +1 並補零
    const day = String(now.getDate()).padStart(2, "0");
    const hour = String(now.getHours()).padStart(2, "0");
    const minute = String(now.getMinutes()).padStart(2, "0");
    const second = String(now.getSeconds()).padStart(2, "0");

    return `${year}/${month}/${day} ${hour}:${minute}:${second}`;
  }
  async function generateCheckMacValue(params) {
    try {
        const res = await ApiTicket.getCheckMacValue(params);
        console.log(res.data);
        return res.data;
    } catch (error) {
        console.error("Error generating CheckMacValue:", error);
        return null;
    }
}
  
export async function generateOrderId() {

  let params = {
    MerchantID: "2000132",
    MerchantTradeNo: generateOrderIdWithRandom(),
    MerchantTradeDate: getCurrentDateTimeFormatted(),
    PaymentType: "aio",
    TotalAmount: "500",
    TradeDesc: "交易描述",
    ItemName: "商品名稱",
    ReturnURL: "http://localhost:8080/pay",
    ClientBackURL: "http://localhost:8080",
    OrderResultURL: "http://localhost:8080/pay",
    ChoosePayment: "ALL",
    EncryptType: "1",
    CustomField1: "",
    CustomField2: "",
    CustomField3: "",
    CustomField4: "",

  };

  
  let checkMacValue = await generateCheckMacValue(params);
  if (checkMacValue) {
      params.CheckMacValue = checkMacValue;
      console.log(params);
  } else {
      console.error("Failed to generate CheckMacValue.");
  }
  
  
  axios.post('https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5', params, {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
  .then(response => {
    console.log('Response:', response.data);
  })
  .catch(error => {
    console.error('Error:', error);
  });

}