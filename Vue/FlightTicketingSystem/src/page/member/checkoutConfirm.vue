<template>
  <h3>結帳成功，確認訂單信件已寄出！</h3>
<br>
  <v-container class="pa-4">  
 
    <v-row v-if="!redeemDetail">
      <v-col class="text-center">
        <v-progress-circular
          indeterminate
          color="primary"
          size="64"
          width="6"
        ></v-progress-circular>
        <div class="mt-4">載入中，請稍後...</div>
      </v-col>
    </v-row>

    <v-row v-if="redeemDetail" class="gap-4">
      <v-col cols="12" >
        <v-card class="pa-6" elevation="6" rounded="xl">
          <v-card-title class="text-h5 mb-4">訂單編號：{{ redeemDetail.redeemId }}</v-card-title>

          <v-list v-if="redeemDetail.redeemItems && redeemDetail.redeemItems.length > 0">
            <v-divider></v-divider>
            <v-list-item v-for="item in redeemDetail.redeemItems" :key="item.redeemItemId">
              <v-list-item-content>
                <v-list-item-title>商品名稱：{{ item.product.name }}</v-list-item-title>
               
                <v-list-item-subtitle>數量：{{ item.quantity }}</v-list-item-subtitle>
                
                <v-list-item-subtitle>單品所需里程：{{ item.product.needmiles }}</v-list-item-subtitle>
            
                <v-list-item-subtitle>小計里程：{{ item.usedMiles*item.quantity }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list>
          <div v-else class="text-center mt-4">
            <v-alert type="warning" title="沒有訂單項目"></v-alert>
          </div>

          <v-divider class="my-4"></v-divider>
          <div class="text-right">
            <span class="font-weight-bold">總里程：{{ redeemDetail.redeemTotalMiles }} 里程</span>
          </div>
        </v-card>
      </v-col>
      <v-btn @click="checkRedeem">管理訂單</v-btn>
      <noticeblock></noticeblock>

    </v-row>
  </v-container>
</template>
  
  <script setup>

import { ApiMember } from '@/utils/API';
import { ref, computed, onMounted,reactive } from 'vue';
import { useRoute,useRouter } from "vue-router"; // 引入 vue-router
  import { ApiRedeem } from '@/utils/API';
import noticeblock from '@/components/product/noticeblock.vue';
// 1. 從 URL 拿到 redeemId
const route = useRoute();

const redeemId = ref(route.query.redeemId);
  const memberId = 2;

  const router = useRouter(); 
// 驗證表單是否通過
  const valid = ref(false);

  // 會員資料（收件人）
const member = reactive({
  fullname: '',
  address: '',
  phone:''
})

// 2. 用來存後端回來的訂單詳情
const redeemDetail = ref(null);

// 3. 抓資料的實作

  onMounted(async () => {
    if (!redeemId.value) {
      console.error('沒有帶入 redeemId，無法抓訂單');
      return;
    }

    console.log(`正在抓訂單 id=${redeemId.value} 的資料…`);

    try {
      const res = await ApiRedeem.getRedeemById(redeemId.value);
      redeemDetail.value = res.data;
      console.log('抓到訂單資料：', redeemDetail.value);
      console.table(redeemDetail.value.redeemItems);

    } catch (e) {
      console.error(' 抓取訂單失敗', e);
    }
  }
  )






  function submitOrder() {
    if (memberId) {
      console.log(memberId);
      
      console.log('送出資料:', formData.value);
      alert('結帳成功！');
        // 這邊可以加 API 請求送出訂單資料
        ApiMember.updateMember
        //成功後寄信

      //跳轉回里程兌換首頁
      router.push("/test2");
    }
  }
  function checkRedeem() {
  router.push("/memberFront");
 }
  </script>
  
  <style scoped>
.v-card-title {
  font-size: 22px;
  font-weight: bold;
}

.v-list-item-title {
  font-size: 18px;
  font-weight: 500;
}

.v-list-item-subtitle {
  font-size: 16px;
  color: #000000;
  margin-top: 16px;
}

.text-right {
  font-size: 18px;
  margin-top: 16px;
}

.v-alert {
  font-size: 16px;
}
</style>
