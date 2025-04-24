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

 <!-- 新增訂單 -->
 <v-btn @click="openAddDialog">新增訂單</v-btn>

    <br /><br />

    <v-data-table :headers="headers" :items="Allredeem" item-key="redeemId">
      <template v-slot:item.actions="{ item }">
        <div class="d-flex ga-2 justify-end">
          <v-icon icon="mdi-pencil" @click="edit(item.redeemId)"></v-icon>
          <v-icon icon="mdi-delete" @click="remove(item.redeemId)"></v-icon>
          <v-icon icon="mdi-cancel" color="red" @click="cancel(item.id)"></v-icon>
          <v-icon icon="mdi-check-circle-outline" color="green" @click="updateStatus(item.id, '已處理')"></v-icon>
        </div>
      </template>
    </v-data-table>

    <br /><br />

    <v-dialog v-model="dialog" max-width="600">
      <v-card>
        <v-card-title>
          {{ updateId ? '修改訂單' : '新增訂單' }}
        </v-card-title>

        <v-card-text>
          <v-form ref="form">
            <v-row>
              <v-col cols="12" md="6">
                <v-text-field v-model="insertRedeem.red" label="訂單編號" required />
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field v-model="insertRedeem.memberId" label="會員 ID" type="number" required />
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field v-model="insertRedeem.redeemDate" label="訂單日期" type="date" required />
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field v-model="insertRedeem.redeemStatus" label="訂單狀態" />
              </v-col>
              </v-row>
          </v-form>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn variant="text" color="red" @click="closeDialog">取消</v-btn>
          <v-btn color="success" @click="saveRedeem">{{ updateId ? '儲存修改' : '新增訂單' }}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

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
  { title: '訂單狀態', value: 'redeemStatus' },
  { title: '訂單日期', value: 'createAt' },
  { title: '總花費', value: 'redeemTotalMiles' },
  // 根據你的 Redeem 實體添加其他欄位標題
  { title: '操作', key: 'actions' },
]);

// 預設表單
const DEFAULT_REDEEM_FORM = { redeemId: '', memberId: null, redeemDate: new Date().toISOString().split('T')[0], redeemStatus: '' };
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
function searchByMemberId(memberId) {
  ApiRedeem.findByMemberId(memberId).then((res) => {
    console.log(res.data);
    Allredeem.value = res.data
  });
}

// 根據會員 ID 查詢訂單
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
  try {
    if (updateId.value) {
      // 修改訂單
      await ApiRedeem.updateRedeem(updateId.value, updateRedeem.value);
      Swal.fire('修改成功', '', 'success').then(() => {
        search();
        closeDialog();
      });
    } else {
      // 新增訂單
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

// 更新訂單狀態
function updateStatus(id, newStatus) {
  ApiRedeem.updateRedeemStatus(id, newStatus)
    .then(() => {
      Swal.fire('狀態更新成功！', `訂單 ${id} 狀態已更新為 ${newStatus}`, 'success');
      search();
    })
    .catch(error => {
      Swal.fire('狀態更新失敗！', error.response?.data?.message || '發生錯誤', 'error');
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
