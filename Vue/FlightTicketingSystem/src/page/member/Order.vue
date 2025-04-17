<template>
    <!-- headers 放logo 登入註冊 換系統選項 -->
     <!-- 標題 字要更大 -->
 <h1>里程兌換</h1>
    <br/>
  
  
<!-- 主畫面 -->
    
 <v-container>
    <br/>
    <br/>
   <!-- 商品名稱搜尋欄 -->
    <v-text-field
      v-model="searchFilters.name"
      append-icon="mdi-magnify"
      label="輸入商品名稱"
      single-line
      hide-details
      class="mx-4"
      @input="searchByName"
    ></v-text-field>
    
<!-- 商品類別菜單 -->
 <!-- 商品里程菜單 -->
    <!-- 商品顯示 一排四個卡片-->
   
 <br/>
 <br/>
 <v-row>
      <v-col
        v-for="(product, index) in Allproducts"
        :key="product.id"
        cols="12"
        md="3"
      >
      <!-- 商品卡按下去跳商品細節頁面 -->
      <v-card 
      @click="selectProduct(product.id)" 
      class="hoverable" style="cursor: pointer;">
             <!-- 商品圖片 -->
          <v-img
            :src="product.image"
            height="200px"
            cover
          >        
        </v-img>
            <!-- 商品名稱 -->
          <v-card-title> {{ product.name }}</v-card-title>
          <!-- 兌換所需里程數 -->
          <v-card-subtitle>{{product.needmiles}}</v-card-subtitle>
     
          
        </v-card>
      </v-col>
    </v-row>
  </v-container>
    
</template>

<script setup>

import { ref, onMounted, computed } from "vue";
// import { useRouter } from 'vue-router'
// import { ApiProducts } from '@/utils/API';


const router = useRouter()
//初始運行函式()
// onMounted(() => {
//   search();
// });
onMounted(() => {
  // 模擬後端回傳的商品資料
  Allproducts.value = [
    {
      id: 1,
      name: "星巴克咖啡券",
      needmiles: 500,
      image: "https://via.placeholder.com/200x150?text=星巴克"
    },
    {
      id: 2,
      name: "電影票兌換券",
      needmiles: 800,
      image: "https://via.placeholder.com/200x150?text=電影票"
    },
    {
      id: 3,
      name: "超商禮券",
      needmiles: 300,
      image: "https://via.placeholder.com/200x150?text=超商禮券"
    },
    {
      id: 4,
      name: "午餐便當",
      needmiles: 400,
      image: "https://via.placeholder.com/200x150?text=便當"
    }
  ];
});

// 商品資料變數
const Allproducts = ref([]);

// 目前選中的商品
const product = ref({
  id: null,
  name: '',
  needmiles: 0,
  image: ''
});


// 搜尋關鍵字
const searchFilters = ref({
    name: '',
    // category: '',
    // minMiles: '',
    // maxMiles: ''
})

//查詢全部函式
// function search() {
//  ApiProducts.getAllProduct().then((res) => {
//     Allproducts.value = res.data;
//     console.log(Allproducts.value);
    
//   });
// }
//根據商品名稱查詢函式
function searchByName() {
const name = searchFilters.value.name;
 ApiProducts.searchProByName(name).then((res) => {
    Allproducts.value = res.data;
    console.log("搜尋結果：",Allproducts.value);
    
  });
}
// 按下查看詳情 跳到商品細節頁面
function selectProduct(id) {
    alert(888)
//     router.push(`/product/${id}`)
}


</script>

<style  scoped>

</style>