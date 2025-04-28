<template>
  <v-container>
    <div>
      <h1>訂單管理</h1>
    </div>
    <br /><br />
 <!-- 關鍵字搜尋欄 -->
 <v-text-field v-model="memberId" label="輸入會員ID" />
 
  <!-- 根據會員id搜尋訂單 -->
  <v-btn prepend-icon="mdi-magnify" @click="searchByMemberId(memberId)"> 會員訂單搜尋 </v-btn>

  <!-- 搜尋全部訂單 -->
  <v-btn prepend-icon="mdi-magnify" @click="search"> 搜尋全部訂單 </v-btn>



    <br /><br />

    <v-data-table :headers="headers" :items="Allredeem" item-key="redeemId">
      <template v-slot:item.actions="{ item }">
        <div class="d-flex gap-2 justify-end">
          <v-select
           v-model="item.redeemStatus"
            :items="redeemStatusOptions"
             item-title="status"
             item-value="status"
             label="訂單狀態"
             required
             dense
             hide-details
             @update:modelValue="val=>updateStatus(item,val)"
         ></v-select>
          <v-btn  @click="remove(item.redeemId)">刪除訂單</v-btn>
          <v-btn  color="red" @click="cancel(item.redeemId)">取消訂單</v-btn>
        
        </div>
      </template>
    </v-data-table>

    <br /><br />

  </v-container>
</template>

<script setup>import { ref, onMounted } from 'vue';
import { ApiRedeem } from '@/utils/API';
import Swal from 'sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';

// 訂單列表
const Allredeem = ref([]);
//搜尋memeberId列表
const memberId = ref([]);
const redeemId = ref();
//對話框
const dialog = ref(false);
// 表格欄位
const headers = ref([
  { title: '訂單編號', value: 'redeemId' },
  { title: '會員 ID', value: 'member.memberId' },

  { title: '訂單日期', value: 'createAt' },
  { title: '總花費', value: 'redeemTotalMiles' },
  // 根據你的 Redeem 實體添加其他欄位標題
  { title: '操作', key: 'actions' },
]);

//訂單狀態
const redeemStatusOptions = ref([
  { id: 1, status: '待處理' },
  { id: 2, status: '處理中' },
  { id: 3, status: '待出貨' },
  { id: 4, status: '已出貨' },
  { id: 5, status: '已送達' },
  { id: 6, status: '已取消' },
  { id: 7, status: '已完成' },
])
// 預設表單
const DEFAULT_REDEEM_FORM = {
  memberId: null,
  redeemItems: [
    { productId: null, quantity: null }
  ]
}
const DEFAULT_REDEEM_UPDATE = { ...DEFAULT_REDEEM_FORM };

// 新增
const insertRedeem = ref({ ...DEFAULT_REDEEM_FORM });

// 修改
const updateRedeem = ref({ ...DEFAULT_REDEEM_UPDATE });
const updateId = ref(null);

// 初始化
onMounted(() => search());



// 查詢全部未刪除的訂單
async function search() {
  try {
    const res = await ApiRedeem.getAllRedeem();
    // 過濾資料，只保留 deleted 為 false 的項目
    Allredeem.value = res.data.filter(item => item.deleted === false);
    console.log("查詢全部未刪除訂單 Response:", res);
    console.log("未刪除訂單資料:", Allredeem.value);
  } catch (error) {
    console.error("查詢全部訂單失敗:", error);
    // 可以在這裡處理錯誤，例如顯示錯誤訊息
  }
}



// 根據會員 ID 查詢訂單
async function searchByMemberId(memberId) {
  try {
    const res = await ApiRedeem.findByMemberId(memberId);
   // 過濾資料，只保留 deleted 為 false 的項目
    Allredeem.value = res.data.filter(item => item.deleted === false);
    console.log("查詢此會員未刪除訂單 Response:", res);
    console.log("未刪除訂單資料:", Allredeem.value);
  } catch (error) {
    console.error("查詢訂單失敗:", error);
    // 可以在這裡處理錯誤，例如顯示錯誤訊息
  }
}

// 根據訂單 ID 查詢訂單
function getRedeemById(redeemId) {
  ApiRedeem.getRedeemById(redeemId).then((res) => {
    console.log(res.data);
      Allredeem.value = res.data
  });
}
// 開啟新增對話框
function openAddDialog() {
  updateId.value = null;
  insertRedeem.value = { ...DEFAULT_REDEEM_FORM };
  dialog.value = true;
}

// 開啟修改對話框
function edit(id) {
  const item = Allredeem.value.find(p => p.id === id);
  updateId.value = id;
  updateRedeem.value = { ...item };
  dialog.value = true;
}

// 關閉對話框
function closeDialog() {
  dialog.value = false;
  insertRedeem.value = { ...DEFAULT_REDEEM_FORM };
  updateRedeem.value = { ...DEFAULT_REDEEM_UPDATE };
  updateId.value = null;
}

// 新增或修改訂單
async function saveRedeem() {
  console.log(insertRedeem.value);
  try {
    if (updateId.value) {
      console.log("修改訂單");
      
      // 修改訂單
      await ApiRedeem.updateRedeem(updateId.value, updateRedeem.value);
      Swal.fire('修改成功', '', 'success').then(() => {
        search();
        closeDialog();
      });
    } else {
      // 新增訂單
      console.log("新增訂單");
      
      await ApiRedeem.addRedeem(insertRedeem.value);
      Swal.fire('新增成功', '', 'success').then(() => {
        search();
        closeDialog();
      });
    }
  } catch (error) {
    Swal.fire('操作失敗', error.response?.data?.message || '請檢查輸入', 'error');
  }
}

// 刪除訂單
function remove(id) {
  console.log(id);
  
  Swal.fire({
    title: '確定要刪除嗎？',
    text: '刪除後將無法恢復！',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#d33',
    cancelButtonColor: '#3085d6',
    confirmButtonText: '確定刪除',
    cancelButtonText: '取消',
  }).then((result) => {
    if (result.isConfirmed) {
      ApiRedeem.softDeleteRedeem(id)
        .then(() => {
          Swal.fire('刪除成功！', '', 'success');
          search();
        })
        .catch(error => {
          Swal.fire('刪除失敗！', error.response?.data?.message || '發生錯誤', 'error');
        });
    }
  });
}

// 假設 item 是從 props 或父層傳進來的
 const props = defineProps(['item'])
// 或你在列表裡用 v-for 的話，item 是迴圈變數

// 更新訂單狀態
const updateStatus = (item,newStatus) => {
  console.log(item);
  console.log(newStatus);
  //const item = Allredeem.value.find(p => p.id === id);
  ApiRedeem.updateRedeemStatus(item.redeemId, String(newStatus))
    .then(() => {
      Swal.fire('狀態更新成功！', `訂單 ${item.redeemId} 狀態已更新為 ${newStatus}`, 'success');
      search();
    })
    .catch(error => {
      console.log(error);

      Swal.fire('狀態更新失敗！', error.response?.data?.message || '發生錯誤', 'error');
      search();
    });
}

// 取消訂單
function cancel(id) {
  Swal.fire({
    title: '確定要取消訂單嗎？',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#d33',
    cancelButtonColor: '#3085d6',
    confirmButtonText: '確定取消',
    cancelButtonText: '取消',
  }).then((result) => {
    if (result.isConfirmed) {
      ApiRedeem.cancelRedeem(id)
        .then(() => {
          Swal.fire('訂單已取消！', '', 'success');
          search();
        })
        .catch(error => {
          Swal.fire('取消訂單失敗！', error.response?.data?.message || '發生錯誤', 'error');
        });
    }
  });
}
</script>

<style scoped>
/* 自訂樣式 */
</style>
