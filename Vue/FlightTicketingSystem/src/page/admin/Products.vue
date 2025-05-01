<template>
  <v-container>
    <div>
      <h1>商品管理</h1>
    </div>
    <br /><br />
    <!-- 新增商品 -->
     
    <v-btn class="mr-2" @click="insertDialog = true">新增商品</v-btn>
    <!-- 搜尋商品 -->
    <v-btn color="secondary" @click="searchByName">
  <v-icon left>mdi-magnify</v-icon>
  搜尋
</v-btn>

    <br /><br />
    <!-- 關鍵字搜尋欄 -->
    <v-text-field v-model="searchFilters.name" label="輸入商品名稱" />
    
    <br /><br />

    

    <!-- 商品表格 -->
    <v-data-table :headers="headers" :items="Allproducts" item-key="id" class="text-left">
      <template v-slot:item.desc="{ item }">
  {{ item.desc.length > 7 ? item.desc.slice(0, 7) + '...' : item.desc }}
</template>

      <!-- 顯示圖片 -->
      <template v-slot:item.image="{ item }">
    <div class="d-flex align-center justify-center py-2">
    <v-img
      :src="'http://localhost:8080' + item.image"
      height="80"
      width="80"
      class="rounded"
      cover
    ></v-img>
     </div>
  </template>
      <template v-slot:item.actions="{ item }">
        <div class="d-flex align-center justify-center py-2">
          <v-icon icon="mdi-pencil" @click="edit(item.id)"></v-icon>
          <v-icon icon="mdi-delete" @click="remove(item.id)"></v-icon>
        </div>
      </template>
      <template v-slot:item.available="{ item }">
    <span>{{ item.available ? '上架' : '下架' }}</span>
  </template>
    </v-data-table>

    


<!-- 新增商品對話框 -->
 <v-dialog v-model="insertDialog" max-width="600">
      <v-card>
        <!-- 標題 -->
        <v-card-title>
        新增商品
        </v-card-title>

        <v-card-text>
          <!-- 表單內容 -->
          <v-form ref="form">
             <!-- 選擇類別 -->
            <v-select
      v-model="insertData.category.categoryId"
      :items="categoryOptions"
      item-title="name"
      item-value="id"
      label="商品類別"
      required
    ></v-select>
        <!-- 商品上下架 -->
        <v-select
            v-model="updateData.available"
            :items="productAvailable"
            item-title="status"
            item-value="id"
            label="上下架"
          />
            <v-row>
        <v-col cols="12" md="6">
          <v-text-field v-model="insertData.name" label="商品名稱" required />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field v-model="insertData.desc" label="商品描述" required />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field v-model="insertData.needmiles" label="所需里程" required />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field v-model="insertData.quantity" label="庫存" required />
        </v-col>
        <v-col cols="12" md="6">
          <v-file-input
            v-model="insertImageFile"
            label="商品照片"
            accept="image/*"
            @change="handleInsertImageChange"
          />
        </v-col>
        <v-col cols="12" md="6">
        <v-btn color="primary" @click="uploadImage">上傳圖片</v-btn> 
      </v-col>
      </v-row>
      
          </v-form>
        </v-card-text>

        <v-divider></v-divider>

        <!-- 操作按鈕 -->
        <v-card-actions>
          <v-spacer></v-spacer>
         
          <v-btn variant="text"color="red" @click="insertDialog = false">取消</v-btn>
    <v-btn color="success" @click="insert">新增商品</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <br /><br />
<!-- 編輯商品用對話框 -->
    <v-dialog v-model="updateDialog" max-width="600">
      <v-card>
        <!-- 標題 -->
        <v-card-title>
          編輯商品
        </v-card-title>

        <v-card-text>
          <!-- 表單內容 -->
          <v-form ref="form">
             <!-- 選擇類別 -->
            <v-select
      v-model="updateData.category.categoryId"
      :items="categoryOptions"
      item-title="name"
      item-value="id"
      label="商品類別"
      required
    ></v-select>
     <!-- 商品上下架 -->
     <v-select
            v-model="updateData.available"
            :items="productAvailable"
            item-title="status"
            item-value="id"
            label="上下架"
          />
            <v-row>
        <v-col cols="12" md="6">
          <v-text-field v-model="updateData.name" label="商品名稱" required />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field v-model="updateData.desc" label="商品描述" required />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field v-model="updateData.needmiles" label="所需里程" required />
        </v-col>
        <v-col cols="12" md="6">
          <v-text-field v-model="updateData.quantity" label="庫存" required />
        </v-col>
        <v-col cols="12" md="6">
          <v-file-input
            v-model="updateImageFile"
            label="商品照片"
            accept="image/*"
            @change="handleUpdateImageChange"
          />
        </v-col>
        <v-col cols="12" md="6">
        <v-btn color="primary" @click="uploadImageForUpdate">上傳圖片</v-btn> 
      </v-col>
      </v-row>
      
          </v-form>
        </v-card-text>

        <v-divider></v-divider>

        <!-- 操作按鈕 -->
        <v-card-actions>
          <v-spacer></v-spacer>
         
          <v-btn variant="text"color="red" @click="updateDialog = false">取消</v-btn>
    <v-btn color="success" @click="update">更新商品</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

   
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
//
const insertDialog = ref(false)
const updateDialog = ref(false)
// 表格欄位
const headers = ref([
  { title: '商品ID', value: 'id' },
  { title: '商品類別', value: 'category.categoryName' },
  { title: '名稱', value: 'name' },
  { title: '描述', value: 'desc' },
  { title: '所需', value: 'needmiles' },
  { title: '庫存', value: 'quantity' },
  { title: '圖片', value: 'image' },
  { title: '上架狀態', value: 'available' },
  { title: '操作', key: 'actions' }
]);
const categoryOptions = [
  { id: 1, name: '酒類' },
  { id: 2, name: '旅行用品' },
  { id: 3, name: '美妝類' },
  { id: 4, name: '電子產品' },
  { id: 5, name: '其他' },
]
const productAvailable = [
  { id:true , status: '上架' },
  { id: false, status: '下架' }
];
// 預設表單
const DEFAULT_FORM = { name: '', desc: '', needmiles: 0, quantity: 0, category: { categoryId: 1 }, image: '' ,available: true};
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
  console.log(searchFilters.value.name);
  
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

function getFileName(path) {
  if (!path) {
    return '';
  }
  const lastSlashIndex = path.lastIndexOf('/');
  if (lastSlashIndex === -1) {
    return path; // 如果沒有斜線，直接返回整個字串
  }
  return path.substring(lastSlashIndex + 1);
}
// 新增商品
async function insert() {
  if (!insertData.value.image) return Swal.fire('請先上傳圖片', '', 'warning');
  try {
    await ApiProducts.addProduct(insertData.value).then(() => {
      insertDialog.value = false;
    });
    Swal.fire('新增成功', '', 'success').then(() => {
      searchByName();
      insertData.value = { ...DEFAULT_FORM };
      insertImageFile.value = null;
    });
  } catch (e) {
    console.log(e);
    
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
  //打開dialog
  updateDialog.value = true;
  //用id找product
  ApiProducts.searchById(id).then((res) => {
    console.log(res.data);
    updateData.value = res.data;
    console.log();
    
  })

}

// 修改商品
async function update() {
  if (!updateData.value.image) return Swal.fire('請先上傳圖片', '', 'warning');
  try {
    await ApiProducts.updateProduct(updateData.value.id, updateData.value)

    Swal.fire('修改成功', '', 'success').then(search);
    updateDialog.value = false;
  } catch (e) {
    Swal.fire('修改失敗', '', 'error');
  }
}





</script>

<style scoped>
/* 自訂樣式 */
</style>
