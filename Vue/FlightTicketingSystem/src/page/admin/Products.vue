<template>
  <v-container>
    <div>
      <h1>商品管理</h1>
    </div>

    <!-- 搜尋按鈕 -->
    <v-btn prepend-icon="mdi-magnify" @click="search"> 搜尋全部 </v-btn>

    <!-- 關鍵字搜尋欄 -->
    <v-text-field v-model="searchFilters.name" label="輸入商品名稱" />
    <v-btn prepend-icon="mdi-magnify" @click="searchByName"> 搜尋 </v-btn>

    <br /><br />

    <!-- 商品表格 -->
    <v-data-table :headers="headers" :items="Allproducts" item-key="id">
      <template v-slot:item.actions="{ item }">
        <div class="d-flex ga-2 justify-end">
          <v-icon icon="mdi-pencil" @click="edit(item.id)"></v-icon>
          <v-icon icon="mdi-delete" @click="remove(item.id)"></v-icon>
        </div>
      </template>
    </v-data-table>

    <br /><br />

    <!-- 新增商品表單 -->
    <v-form>
      <v-row>
        <v-col>
          <v-text-field v-model="insertData.name" label="商品名稱" required />
        </v-col>
        <v-col>
          <v-text-field v-model="insertData.desc" label="商品描述" required />
        </v-col>
        <v-col>
          <v-text-field v-model="insertData.needmiles" label="所需里程" required />
        </v-col>
        <v-col>
          <v-text-field v-model="insertData.quantity" label="庫存" required />
        </v-col>
        <v-col>
          <v-file-input
            v-model="insertImageFile"
            label="商品照片"
            accept="image/*"
            @change="handleInsertImageChange"
          />
        </v-col>
      </v-row>
    </v-form>
    <v-btn color="primary" @click="uploadImage">上傳圖片</v-btn>
    <v-btn color="success" @click="insert">新增商品</v-btn>

    <br /><br />

    <!-- 修改商品表單 -->
    <v-form>
      <v-row>
        <v-col>
          <v-text-field v-model="updateData.name" label="商品名稱" required />
        </v-col>
        <v-col>
          <v-text-field v-model="updateData.desc" label="商品描述" required />
        </v-col>
        <v-col>
          <v-text-field v-model="updateData.needmiles" label="所需里程" required />
        </v-col>
        <v-col>
          <v-text-field v-model="updateData.quantity" label="庫存" required />
        </v-col>
        <v-col>
          <v-file-input
            v-model="updateImageFile"
            label="商品照片"
            accept="image/*"
            @change="handleUpdateImageChange"
          />
          <div v-if="updateData.image">
            目前圖片：<img :src="updateData.image" height="100" />
          </div>
        </v-col>
      </v-row>
    </v-form>
    <v-btn color="primary" @click="uploadImageForUpdate">上傳修改圖片</v-btn>
    <v-btn color="success" @click="update">修改商品</v-btn>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ApiProducts } from '@/utils/API';
import Swal from 'sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';

// 商品列表
const Allproducts = ref([]);
// 搜尋關鍵字
const searchFilters = ref({ name: '' });

// 表格欄位
const headers = ref([
  { title: '商品 ID', value: 'id' },
  { title: '名稱', value: 'name' },
  { title: '描述', value: 'desc' },
  { title: '所需里程', value: 'needmiles' },
  { title: '庫存', value: 'quantity' },
  { title: '圖片', value: 'image' },
  { title: '操作', key: 'actions' }
]);

// 預設表單
const DEFAULT_FORM = { name: '', desc: '', needmiles: 0, quantity: 0, category: { categoryId: 1 }, image: '' };
const DEFAULT_UPDATE = { ...DEFAULT_FORM };

// 新增
const insertData = ref({ ...DEFAULT_FORM });
const insertImageFile = ref(null);

// 修改
const updateData = ref({ ...DEFAULT_UPDATE });
const updateImageFile = ref(null);
const updateId = ref(null);

// 初始化
onMounted(() => search());

// 查詢全部
function search() {
  ApiProducts.getAllProduct().then(res => Allproducts.value = res.data);
}
// 按名稱查詢
function searchByName() {
  ApiProducts.searchProByName(searchFilters.value.name).then(res => Allproducts.value = res.data);
}

// 檔案改變
const handleInsertImageChange = e => insertImageFile.value = e.target.files[0] || null;
const handleUpdateImageChange = e => updateImageFile.value = e.target.files[0] || null;

// 上傳新增圖片
async function uploadImage() {
  if (!insertImageFile.value) return Swal.fire('請選擇圖片', '', 'warning');
  const formData = new FormData();
  formData.append('image', insertImageFile.value);
  try {
    const res = await ApiProducts.uploadProductImage(0, formData);
    insertData.value.image = res.data.url;
    Swal.fire('圖片上傳成功', '', 'success');
  } catch (e) {
    Swal.fire('上傳失敗', '', 'error');
  }
}
// 上傳修改圖片
async function uploadImageForUpdate() {
  if (!updateImageFile.value) return Swal.fire('請選擇圖片', '', 'warning');
  const formData = new FormData();
  formData.append('image', updateImageFile.value);
  try {
    const res = await ApiProducts.uploadProductImage(updateId.value, formData);
    updateData.value.image = res.data.url;
    Swal.fire('圖片上傳成功', '', 'success');
  } catch (e) {
    Swal.fire('上傳失敗', '', 'error');
  }
}

// 新增商品
async function insert() {
  if (!insertData.value.image) return Swal.fire('請先上傳圖片', '', 'warning');
  try {
    await ApiProducts.addProduct(insertData.value);
    Swal.fire('新增成功', '', 'success').then(() => {
      searchByName();
      insertData.value = { ...DEFAULT_FORM };
      insertImageFile.value = null;
    });
  } catch (e) {
    Swal.fire('新增失敗', '', 'error');
  }
}

// 刪除
function remove(id) {
  ApiProducts.deleteProduct(id).then(() => {
    Swal.fire('刪除成功', '', 'success');
    search();
  });
}
// 編輯
function edit(id) {
  const item = Allproducts.value.find(p => p.id === id);
  updateId.value = id;
  updateData.value = { ...item };
}

// 修改商品
async function update() {
  if (!updateData.value.image) return Swal.fire('請先上傳圖片', '', 'warning');
  try {
    await ApiProducts.updateProduct(updateId.value, updateData.value);
    Swal.fire('修改成功', '', 'success').then(search);
  } catch (e) {
    Swal.fire('修改失敗', '', 'error');
  }
}
</script>

<style scoped>
/* 自訂樣式 */
</style>
