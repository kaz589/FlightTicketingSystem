<template>
  <v-container>
    <div>
      <h1>訂單管理</h1>
    </div>
    <br /><br />
    <div  class="align-center d-flex gap-2 justify-end">
 <!-- 關鍵字搜尋欄 -->
 <v-text-field v-model="memberId" label="輸入會員ID" />
 </div>
 <div  class="align-center d-flex gap-2 ">
  <!-- 根據會員id搜尋訂單 -->
  <v-btn color="primary" prepend-icon="mdi-magnify" @click="searchByMemberId(memberId)"> 會員訂單搜尋 </v-btn>

  <!-- 搜尋全部訂單 -->
  <v-btn  color="secondary" prepend-icon="mdi-magnify" @click="search"> 搜尋全部訂單 </v-btn>
</div>


    <br /><br />

    <v-data-table :headers="headers" :items="Allredeem" item-key="redeemId">
      <template v-slot:item.actions="{ item }">
        <div class="align-center d-flex gap-2 justify-end" >
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
          <v-btn color="red" @click="remove(item.redeemId)">刪除訂單</v-btn>
          <v-btn color="light-blue" @click="openRedeemDetailDialog(item.redeemId)">
          查看明細
        </v-btn>
        </div>
      </template>
    </v-data-table>
  

    <br /><br />

 <!-- 里程兌換訂單明細查看 -->
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


  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ApiRedeem,ApiRedeemItem } from '@/utils/API';
import Swal from 'sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';

// 訂單列表
const Allredeem = ref([]);
const redeemItems = ref([]);
//搜尋memeberId列表
const memberId = ref([]);
const redeemId = ref();
const redeemItemId = ref();
//對話框
const dialog = ref(false);
const checkRedeemDetailDialog = ref(false);
// 訂單表格欄位
const headers = ref([
  { title: '訂單編號', value: 'redeemId' },
  { title: '會員 ID', value: 'member.memberId' },
  { title: '訂單日期', value: 'createAt' },
  { title: '總花費', value: 'redeemTotalMiles' },
  // 根據你的 Redeem 實體添加其他欄位標題
  { title: '操作', key: 'actions' },
]);
// 訂單項目表格欄位
const RedeemItemheaders = ref([
  { title: '訂單項目編號', value: 'redeemItemId' },
 
  //這裡顯示商品名稱
  { title: '商品名稱', value: 'product.name' },
  { title: '兌換數量', value: 'quantity' },
  { title: '所需里程', value: 'usedMiles' },
]);



//訂單狀態
const redeemStatusOptions = ref([
  { id: 1, status: '待處理' },
  { id: 2, status: '處理中' },
  { id: 3, status: '待出貨' },
  { id: 4, status: '已出貨' },
  { id: 5, status: '已取消' },
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




// 關閉對話框
function closeDialog() {
  dialog.value = false;
  insertRedeem.value = { ...DEFAULT_REDEEM_FORM };
  updateRedeem.value = { ...DEFAULT_REDEEM_UPDATE };
  updateId.value = null;
}

// 新增或修改訂單(現在沒有用到)
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


async function openRedeemDetailDialog(id) {
  await searchRedeemItems(id); // 抓明細
  checkRedeemDetailDialog.value = true; // 開彈窗
}
</script>

<style scoped>
/* 自訂樣式 */
</style>
