<template>
    <div>
      <h2>結帳頁面</h2>
    </div>
    <v-container>
      <v-row>
        <v-col
          v-for="selectproduct in selectcarts"
          :key="selectproduct.id"
          cols="12"
          md="3"
        >
          {{ selectproduct }}
          <selectProductCard :product="selectproduct" />
        </v-col>
      </v-row>
      <div class="mt-4">
        <v-btn @click="">結帳</v-btn>
        <span class="ml-4">商品種類數：{{ productTypesCount }}</span>
      </div>
    </v-container>
  </template>
  
  <script setup>
  import { onMounted } from "vue";
  import { storeToRefs } from "pinia";
  import { usecartStore } from "@/stores/usecartStore";
  import selectProductCard from "@/components/product/selectProductCard.vue";
  
  const cartStore = usecartStore();
  const { selectcarts, productTypesCount } = storeToRefs(cartStore);
  
  onMounted(() => {
    console.log(selectcarts.value);
  });
  </script>