<template>
  <div class="redeem-order-list">
    <h2 class="text-h5 mb-4">我的兌換訂單     剩餘里程：{{remainingMiles}}</h2>

  
    <v-data-table
      :headers="headers"
      :items="redeems"
      class="elevation-1"
      item-value="redeemId"
    >
      <template #item.actions="{ item }">
        <v-btn
          v-if="item.status !== '已出貨'"
          color="error"
          size="small"
          @click="cancelRedeem(item.redeemId)"
        >
          取消訂單
        </v-btn> <v-btn
          v-if="item.status !== '已完成'"
          color="blue"
          size="small"
          @click="openRedeemDetailDialog(item.redeemId)">
        查看明細
        </v-btn>
        <span v-else class="text-grey">不可取消</span>
      </template>
    </v-data-table>
    <v-dialog v-model="checkRedeemDetailDialog" max-width="800">
<v-card>
  <v-card-title>兌換明細</v-card-title>

  <v-card-text>
    <v-data-table
      :headers="RedeemItemheaders"
      :items="redeemItems"
      item-key="redeemItemId"
      hide-default-footer
      dense
      class="mt-2"
    >
      <template v-slot:item.product="{ item }">
        {{ item.product.name }}
      </template>
      <template v-slot:item.quantity="{ item }">
        {{ item.quantity }}
      </template>
      <template v-slot:item.usedMiles="{ item }">
        {{ item.usedMiles }}
      </template>
    </v-data-table>
  </v-card-text>

  <v-card-actions>
    <v-spacer></v-spacer>
    <v-btn text @click="checkRedeemDetailDialog = false">關閉</v-btn>
  </v-card-actions>
</v-card>
</v-dialog>

  </div>
</template>

<script setup>
import { ref, onMounted,computed } from 'vue'
import { ApiRedeem,ApiRedeemItem,ApiMember } from '@/utils/API';
import { useRouter } from "vue-router"; // 引入 vue-router
import { useAuthStore } from "@/stores/auth";
import dayjs from 'dayjs';

const authStore = useAuthStore();
const memberId = authStore.user.memberId;
const router = useRouter(); 

const remainingMiles = ref(0);
// 表格欄位
const headers = [
  { title: '訂單編號', value: 'redeemId' },
  { title: '下單日期', value: 'createAt' },
  { title: '狀態', value: 'redeemStatus' },
  { title: '總金額', value: 'redeemTotalMiles' },
  { title: '取消訂單', value: 'actions', sortable: false },
]
// 訂單項目表格欄位
const RedeemItemheaders = ref([
{ title: '訂單項目編號', value: 'redeemItemId' },

//這裡顯示商品名稱
{ title: '商品名稱', value: 'product.name' },
{ title: '兌換數量', value: 'quantity' },
{ title: '所需里程', value: 'usedMiles' },
]);

// 訂單資料
const redeems = ref([])
const redeemItems = ref([]);
//查看明細對話框
const checkRedeemDetailDialog = ref(false);
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
// 根據訂單ID查詢訂單明細
async function searchRedeemItems(id) {
if (!id) {
  console.error('沒有帶入 redeemId');
  return;
}
try {
  const res = await ApiRedeemItem.getItemsByRedeemId(id);
  redeemItems.value = res.data;
} catch (e) {
  console.error('抓取訂單明細失敗', e);
}
}
//查看明細
async function openRedeemDetailDialog(id) {
await searchRedeemItems(id); // 抓明細
checkRedeemDetailDialog.value = true; // 開彈窗
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
  fetchRemainingMiles(memberId)
  .catch((error) => {
console.error('取消訂單失敗', error);

const message = error.response?.data?.message || '取消失敗，請稍後再試';
alert(`${message}`);
});
};
async function fetchRemainingMiles(memberId) {
  const res = await ApiMember.getMember(memberId);
  remainingMiles.value = res.data.remainingMiles;
}

// 載入時撈資料
onMounted(() => {
  fetchRedeems(memberId),
  fetchRemainingMiles(memberId)
})
</script>

<style scoped>
.redeem-order-list {
  padding: 20px;
}
</style>
