<template>
<v-container>
  <v-row dense>
    <v-col
      v-for="selectproduct in selectcarts"
      :key="selectproduct.id"
      cols="12"
    >
      <selectProductCard :product="selectproduct" />
    </v-col>
  </v-row>
  <div style="text-align: center; margin-top: 20px;">
  <h3 style="margin-bottom: 20px;">總金額：{{ totalRedeemMiles }} 里程</h3>
  <v-btn color="secondary" class="mx-2" @click="continueExchange">繼續兌換</v-btn>
  <v-btn color="primary" class="mx-2" @click="checkout">結帳</v-btn>
</div>

</v-container>

  </template>
  
  <script setup>
  import { ref,onMounted, computed } from "vue"
  import { storeToRefs } from "pinia";
  import { usecartStore } from "@/stores/usecartStore";
  import selectProductCard from "@/components/product/selectProductCard.vue";
  import { useRouter } from "vue-router"; // 引入 vue-router
import { ApiRedeem } from "@/utils/API";

const cartStore = usecartStore();
  const { productTypesCount,selectcarts } = storeToRefs(cartStore)

 
  onMounted(() => {

console.log(selectcarts);
}
)

const router = useRouter(); 
//計算總金額
const totalRedeemMiles = computed(() => {
  return selectcarts.value.reduce((sum, product) => {
    return sum + product.needmiles * product.quantity;
  }, 0);
});

//導回商品列表的邏輯，例如 router.push
  function continueExchange() {
    router.push("/test2");
    console.log('繼續兌換');
  }
  
  // 結帳的邏輯

  async function checkout() {
  try {
    console.log("準備送出商品資料...");

    const payload = {
      memberId: 2, // 先寫死，之後要用登入的ID
      redeemItems: selectcarts.value.map(product => ({
        productId: product.id,
        quantity: product.quantity
      }))
    };
    console.log("送出的payload：", payload);


    const res = await ApiRedeem.addRedeem(payload);
    console.log("結帳成功：", res);
    // alert("結帳成功!");
    router.push("/checkoutconfirm"); // 成功後跳轉
  } catch (error) {
    console.error("結帳失敗：", error);
    // 判斷是不是自訂錯誤訊息
  // 嘗試取得後端回傳的錯誤訊息
    if (error.response && error.response.data) {
      const errorMessage = error.response.data.message || JSON.stringify(error.response.data);
      alert(`結帳失敗：${errorMessage}`);
    } else {
      alert("結帳失敗，請稍後再試");
    }
  }
}


  

  

  </script>
  