<template>
    
<!-- 商品類別菜單 -->
 <!-- 商品里程菜單 -->


    <!-- 商品顯示 一排四個卡片-->
    <v-container>
        <br/>
     <br/>
    <!-- 商品名稱搜尋欄 -->
     <v-text-field
    
     
      label="輸入商品名稱"
      hide-details
      class="mx-4"
      v-model="searchFilters.name"
      ></v-text-field> 
      <br/>
      <br/>
      <v-btn @click="searchByName(name)">搜尋</v-btn>
    <br/>
    <br/>
      <v-row>
        <v-col
          v-for="product in Allproducts"
          :key="product.id"
          cols="12"
          md="4"
        >
        <productcard :product="product">

          </productcard>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
<script setup>
import { ref, onMounted } from 'vue';
import { ApiProducts } from '@/utils/API';
import Swal from 'sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
import productcard from '@/components/product/productcard.vue';
onMounted(() => {
  search()
})
// 商品列表
const Allproducts = ref([]);

// 搜尋關鍵字
const searchFilters = ref({ name: '' });

function search(){ 
  ApiProducts.getAllProduct().then((res) => {
    Allproducts.value = res.data.filter(p => p.available === true);
    console.log(res.data);
    
  })
}
function searchByName() {
  console.log(searchFilters.value.name);
  
  ApiProducts.searchProByName(searchFilters.value.name).then(res => {
    Allproducts.value = res.data.filter(p => p.available === true);
  });
}


</script>

<style  scoped>

</style>
