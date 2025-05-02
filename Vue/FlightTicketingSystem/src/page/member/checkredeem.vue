<template>
  <div>
    <div class="redeem-order-list">
      <h2 class="text-h5 mb-4">我的兌換訂單</h2>

      <v-data-table :headers="headers" :items="redeems" class="elevation-1" item-value="redeemId">
        <template #item.actions="{ item }">
          <v-btn v-if="item.status !== '已出貨'" color="error" size="small" @click="cancelRedeem(item.redeemId)">
            取消訂單
          </v-btn> <v-btn v-if="item.status !== '已完成'" color="blue" size="small"
            @click="checkRedeemDetail(item.redeemId)">
            查看訂單明細
          </v-btn>
          <span v-else class="text-grey">不可取消</span>
        </template>
      </v-data-table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, } from 'vue'
import { ApiRedeem } from '@/utils/API';
import { useRouter } from "vue-router"; // 引入 vue-router
import { useAuthStore } from "@/stores/auth";
import dayjs from 'dayjs';

const authStore = useAuthStore();
const memberId = authStore.user.memberId;
const router = useRouter();
// 表格欄位
const headers = [
  { title: '訂單編號', value: 'redeemId' },
  { title: '下單日期', value: 'createAt' },
  { title: '狀態', value: 'redeemStatus' },
  { title: '總金額', value: 'redeemTotalMiles' },
  { title: '取消訂單', value: 'actions', sortable: false },
]

// 訂單資料
const redeems = ref([])

async function fetchRedeems(memberId) {
  try {
    const res = await ApiRedeem.findByMemberId(memberId);
    // 過濾資料，只保留 deleted 為 false 的項目
    redeems.value = res.data.filter(item => item.deleted === false);
    console.log("查詢此會員未刪除訂單 Response:", res);
    console.log("未刪除訂單資料:", redeems.value);
  } catch (error) {
    console.error("查詢訂單失敗:", error);
    // 可以在這裡處理錯誤，例如顯示錯誤訊息
  }
}
//查看明細
const checkRedeemDetail = (redeemId) => {
  router.push({
    path: "/checkoutconfirm",
    query: { redeemId: redeemId } // 帶著redeemId跳轉
  });
}
// 取消訂單
const cancelRedeem = (redeemId) => {
  console.log('redeemId:', redeemId);

  if (!confirm('確定要取消這筆訂單嗎？')) {
    return; // 如果按「取消」，直接跳出，不要送出 API
  }

  ApiRedeem.cancelRedeem(redeemId)
    .then(() => {
      alert('訂單已取消');
      fetchRedeems(memberId) // 重新抓一次
    })
    .catch((error) => {
      console.error('取消訂單失敗', error);

      const message = error.response?.data?.message || '取消失敗，請稍後再試';
      alert(`${message}`);
    });
};


// 載入時撈資料
onMounted(() => {
  fetchRedeems(memberId)
})
</script>

<style scoped>
.redeem-order-list {
  padding: 20px;
}
</style>