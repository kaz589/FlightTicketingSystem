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
    <!-- <v-btn color="primary" @click="insert">新增商品</v-btn> -->
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
        <div v-show="updateData.image">
          目前圖片：<img :src="updateData.image" height="60" /></div>
      </v-col>
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

// 預設表單初始值
const DEFAULT_FORM = {
  name: '',
  desc: '',
  needmiles: 0,
  quantity: 0,
  category: { categoryId: 1 }
};
const DEFAULT_UPDATE = { ...DEFAULT_FORM, image: '' };







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
const insertData = ref({ ...DEFAULT_FORM });
const insertImageFile = ref(null);// 用於儲存選擇的圖片檔案
const handleInsertImageChange = (files) => {
  insertImageFile.value = files[0] || null;
};
const insert = async () => {
  const formData = new FormData();
  // 動態 append 所有欄位
  Object.entries(insertData.value).forEach(([key, val]) => {
    if (key === 'category') {
      formData.append('category.categoryId', val.categoryId);
    } else {
      formData.append(key, val);
    }
  });
  // 圖片
  if (insertImageFile.value) { 
    formData.append('image',insertImageFile.value)
  }
  try {
    const response = await ApiProducts.uploadImage(0,formData);
  Swal.fire("新增成功",  JSON.stringify(response.data), 'success').then(() => {
    search();
    insertData.value = { ...DEFAULT_FORM.value }// 清空表單
    insertImageFile.value = null;// 清空圖片選擇

  });
  console.log("新增商品成功:", response);
  } catch (error) {
    console.error("新增商品失敗:", error);
    Swal.fire("新增失敗", error.response?.data?.message || "上傳發生錯誤", "error");
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

// 修改商品
const updateData = ref({ ...DEFAULT_UPDATE });
const updateImageFile = ref(null);
const updateId = ref(1);
const handleUpdateImageChange = (files) => {
  updateImageFile.value =files.target.files[0];
  console.log(updateImageFile.value);
  console.log(files);
  
};

const update = async () => {
  const formData = new FormData();
  // Object.entries(updateData.value).forEach(([key, val]) => {
  //   if (key === 'category') {
  //     formData.append('category.categoryId', val.categoryId);
  //   } else if (key !== 'image') {
  //     formData.append(key, val);
  //   }
  // });
  console.log(updateImageFile.value);
  
  if (updateImageFile) {
    formData.append('image', updateImageFile.value);
  }

  try {
    
    const response = await ApiProducts.uploadImage(updateId.value, formData);
    Swal.fire('修改成功', JSON.stringify(response), 'success').then(() => {
      search();
    });
    console.log('修改商品成功:', response);
  } catch (error) {
    console.error('修改商品失敗:', error);
    Swal.fire('修改失敗', error.response?.data || '上傳發生錯誤', 'error');
  }
};

</script>

<style  scoped>

</style>