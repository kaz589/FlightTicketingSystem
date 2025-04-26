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
      ></v-text-field> 
      <br/>
      <br/>
      <v-btn @click="search">搜尋</v-btn>
    <br/>
    <br/>
      <v-row>
        <v-col
          v-for="product in products"
          :key="product.id"
          cols="12"
          md="3"
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

// 商品列表
const Allproducts = ref([]);
const products = ref([]);

function search(){ 
  ApiProducts.getAllProduct().then((res) => {
    products.value = res.data;
    console.log(res.data);
    
  })
}

</script>

<style  scoped>

</style>