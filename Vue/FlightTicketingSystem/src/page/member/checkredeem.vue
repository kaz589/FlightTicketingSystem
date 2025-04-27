<template>
    <div class="redeem-order-list">
      <h2 class="text-h5 mb-4">我的兌換訂單</h2>
  
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
          </v-btn>
          <span v-else class="text-grey">不可取消</span>
        </template>
      </v-data-table>
    </div>
  </template>
  
  <script setup>
import { ref, onMounted } from 'vue'
import { ApiRedeem } from '@/utils/API';
const memberId = 2;

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
  
  // 取得訂單列表
  const fetchRedeems = async () => {
    try {
      const response = await ApiRedeem.findByMemberId(memberId)
    redeems.value = response.data
    } catch (error) {
      console.error('取得訂單失敗', error)
    }
  }
  
  // 取消訂單
  const cancelRedeem = async (redeemId) => {
    if (!confirm('確定要取消這筆訂單嗎？')) {
      return
    }
  
    try {
        await ApiRedeem.cancelRedeem(redeemId);
      alert('訂單已取消')
      fetchRedeems() // 重新抓一次
    } catch (error) {
      console.error('取消訂單失敗', error)
      alert('取消失敗，請稍後再試')
    }
  }
  
  // 載入時撈資料
  onMounted(() => {
    fetchRedeems()
  })
  </script>
  
  <style scoped>
  .redeem-order-list {
    padding: 20px;
  }
  </style>
  