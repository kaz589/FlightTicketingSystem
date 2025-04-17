<template>
    <v-container>
    <div>
<h1>商品管理</h1>
    </div>

  <!-- 搜尋按鈕 -->
  <v-btn prepend-icon="mdi-magnify" @click="search"> 搜尋全部 </v-btn>
  <!-- 全部資料: {{Allproducts}} -->
 

   <!-- 關鍵字搜尋欄 -->
   <v-text-field v-model="searchFilters.name" label="輸入商品名稱"></v-text-field>


   <!-- 搜尋按鈕 -->
    <v-btn prepend-icon="mdi-magnify" @click="searchByName"> 搜尋 </v-btn>
    <!-- 新增按鈕 -->
    <v-btn color="primary" @click="insert">新增商品</v-btn>
<br />
<br />
   <!-- 顯示欄位 裡面有刪除和修改 -->
<!-- v-if="Allproducts.length > 0" -->
  <v-data-table
    
    :headers="headers"
    :items="Allproducts"
    item-key="id"
  >
    <template v-slot:item.actions="{ item }">
      <div class="d-flex ga-2 justify-end">
        <!-- 調用edit函數 -->
        <v-icon
          color="medium-emphasis"
          icon="mdi-pencil"
          size="small"
          @click="edit(item.id)"
        ></v-icon>

        <!-- 調用deleteById函數 -->
        <v-icon
          color="medium-emphasis"
          icon="mdi-delete"
          size="small"
           @click="remove(item.id)"
        ></v-icon>
      </div>
    </template>
  </v-data-table>

 <br>
 <br>

 取得資料
 <v-form>
    <v-row>
      <v-col
        ><v-text-field
          v-model="insertData.name"
          :counter="10"
          label="商品名稱"
          required
        ></v-text-field
      ></v-col>
      <v-col
        ><v-text-field
          v-model="insertData.desc"
          :counter="10"
          label="商品描述"
          required
        ></v-text-field
      ></v-col>
      <v-col
        ><v-text-field
          v-model="insertData.needmiles"
          :counter="10"
          label="商品兌換所需里程"
          required
        ></v-text-field
      ></v-col>
      <v-col
        ><v-text-field
          v-model="insertData.quantity"
          :counter="10"
          label="商品庫存"
          required
        ></v-text-field
      ></v-col>
      <v-col
        ><v-file-input
        v-model="insertImageFile"
        label="商品照片"
        accept="image/*"
        required
        @change="handleInsertImageChange"
      ></v-file-input>
    </v-col>
    
    </v-row>
  </v-form>
  <v-btn prepend-icon="mdi mdi-account-plus" @click="insert"> 新增商品 </v-btn>
            
<br>
<br>
<v-form>
    <v-row>
      <v-col
        ><v-text-field
          v-model="updateData.name"
          :counter="10"
          label="商品名稱"
          required
        ></v-text-field
      ></v-col>
      <v-col
        ><v-text-field
          v-model="updateData.desc"
          :counter="10"
          label="商品描述"
          required
        ></v-text-field
      ></v-col>
      <v-col
        ><v-text-field
          v-model="updateData.needmiles"
          :counter="10"
          label="商品兌換所需里程"
          required
        ></v-text-field
      ></v-col>
      <v-col
        ><v-text-field
          v-model="updateData.quantity"
          :counter="10"
          label="商品庫存"
          required
        ></v-text-field
      ></v-col>
      <v-col
        ><v-file-input
          v-model="updateImageFile"
          :counter="10"
          label="商品照片"
         accept = "image/*"
         @change="handleUpdateImageChange"
        ></v-file-input>
        <div v-if="updateData.image">目前圖片: {{ updateData.image }}</div>
      ></v-col>
    </v-row>
  </v-form>
  <v-btn prepend-icon="mdi mdi-account-plus" @click="update"> 修改商品 </v-btn>
</v-container>
</template>

<script setup>
import { ref,onMounted} from "vue";
import { ApiProducts } from '@/utils/API';
import Swal from "sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";
//初始運行函式()
onMounted(() => {
  search();
});

// 商品資料變數
const Allproducts = ref([]);

//新增商品表單相關
const DEFAULT_FORM = ref({
name: "",
  desc: "",
  needmiles: 0,
  quantity: 0,
  image: "",// 後端返回的圖片路徑
  category: {
    categoryId: 1
  },
});


const insertData = ref({ ...DEFAULT_FORM.value });
const insertImageFile = ref(null);// 用於儲存選擇的圖片檔案

const handleInsertImageChange = (files) => {
  insertImageFile.value = files[0];// 取得選取的第一個檔案
}

//修改商品表單相關
const DEFAULT_UPDATE = ref({
    name: "",
  desc: "",
  needmiles: 0,
  quantity: 0,
  image: "",
  category: {
    categoryId: 1
  },
});
const updateData = ref({ ...DEFAULT_UPDATE.value });
const updateImageFile = ref(null); // 用於儲存選擇的圖片檔案 (用於更新)

const updateId = ref(1);
const handleUpdateImageChange = (files) => {
  updateImageFile.value = files ? files[0] : null; // 取得選取的第一個檔案，如果取消選擇則為 null
};

//搜尋用輸入名稱
const searchFilters = ref({
  name: "" // 使用者輸入的商品名稱關鍵字
});


// 表格的欄位標題
const headers = ref([
  { title: "商品 ID", value: "id", sortable: true, align: "start" }, // sortable: true 表示可排序
 
  { title: "商品名稱", value: "name", sortable: true },
  { title: "商品描述", value: "desc", sortable: true },
  { title: "商品兌換所需里程", value: "needmiles", sortable: true },
  { title: "剩餘庫存", value: "quantity", sortable: true },
  { title: "商品圖片", value: "image", sortable: false },
  { title: "操作", key: "actions", align: "end", sortable: false }
]);



//查詢全部函式
function search() {
 ApiProducts.getAllProduct().then((res) => {
    Allproducts.value = res.data;
    console.log(Allproducts.value);
    
  });
}

//根據商品名稱查詢函式
function searchByName() {
const name = searchFilters.value.name;
 ApiProducts.searchProByName(name).then((res) => {
    Allproducts.value = res.data;
    console.log("搜尋結果：",Allproducts.value);
    
  });
}

//根據商品id查詢函式 
function searchOne() {
 ApiProducts.searchById(id).then((res) => {
    Allproducts.value = res.data;
    console.log("搜尋結果：",Allproducts.value);
    
  });
}
//新增商品函式(包含圖片上傳)
async function insert() {
  const formData = new FormData();
  formData.append('name', insertData.value.name);
  formData.append('desc', insertData.value.desc);
  formData.append('needmiles', insertData.value.needmiles);
  formData.append('quantity', insertData.value.quantity);
  formData.append('category.categoryId', insertData.value.category.categoryId);
  if (insertImageFile.value) {
    formData.append('image',insertImageFile.value)
  }
  try {
  const response = await axios.post('/products/0/uploadImage', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
  Swal.fire("新增成功", response.data, "success").then(() => {
    search();
    insertData.value = { ...DEFAULT_FORM.value }// 清空表單
    insertImageFile.value = null;// 清空圖片選擇

  });
  console.log("新增商品成功:", response.data);
  } catch (error) {
    console.error("新增商品失敗:", error);
    Swal.fire("新增失敗", error.response?.data || "上傳發生錯誤", "error");
  }
}



//刪除商品函式
function remove(id) {
    ApiProducts.deleteProduct(id).then(() => {
        // alert("刪除成功!");
        Swal.fire("成功刪除", "", "success");

        console.log("刪除ID : " +id);
        search();
      }); 
  };

function edit(id) {
    console.log(id);
    const found = Allproducts.value.find((item) => item.id === id);
    found.id = id;
console.log(found);
    updateData.value = { ...found }

    console.log("XXXXXX"+updateData.value.id);
    
   
    };

//修改商品函式 (包含圖片更新)
async function update() {
  const formData = new FormData();
  formData.append('name', updateData.value.name);
  formData.append('desc', updateData.value.desc);
  formData.append('needmiles', updateData.value.needmiles);
  formData.append('quantity', updateData.value.quantity);
  formData.append('category.categoryId', updateData.value.category.categoryId);
  if (updateImageFile.value) {
    formData.append('image', updateImageFile.value);
  }
  try {
    const response = await axios.post(`/products/${updateData.value.id}/uploadImage`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    Swal.fire("修改成功", response.data, "success").then(() => {
      search();
      updateImageFile.value = null;
    });
    console.log("編輯商品成功:", response.data);
  } catch (error) {
    console.error("編輯商品失敗:", error);
    Swal.fire("編輯失敗", error.response?.data || "更新發生錯誤", "error");
  }
}

</script>

<style  scoped>

</style>