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
        ><v-text-field
          v-model="insertData.image"
          :counter="10"
          label="商品照片"
          required
        ></v-text-field
      ></v-col>
    
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
        ><v-text-field
          v-model="updateData.image"
          :counter="10"
          label="商品照片"
          required
        ></v-text-field
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

//新增會員

const DEFAULT_FORM = ref({
name: "",
  desc: "",
  needmiles: 0,
  quantity: 0,
  image: "",
  category: {
    categoryId: 1
  },
});


const insertData = ref({ ...DEFAULT_FORM.value });

//搜尋用輸入名稱
const searchFilters = ref({
  name: "" // 使用者輸入的商品名稱關鍵字
});


// 表格的欄位標題
const headers = ref([
  { title: "商品 ID", value: "name", sortable: true, align: "start" }, // sortable: true 表示可排序
 
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


//新增商品函式
function insert() {
    ApiProducts.addProduct(insertData.value).then((res) => {
        insertData.value = res.data;
        searchByName();
        console.log("新增商品：",  insertData.value);
    });
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
//修改商品函式
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
const updateId = ref(1);

function edit(id) {
    console.log(id);
    const found = Allproducts.value.find((item) => item.id === id);
    found.id = id;
    console.log(found);
    updateData.value = { ...found }

    console.log("XXXXXX"+updateData.value.id);
    
   
    };

function update() {
    ApiProducts.updateProduct(updateData.value.id,updateData.value).then((res) => {      
        searchByName();
        console.log("編輯商品：", found.value);
       
})
 }


</script>

<style  scoped>

</style>