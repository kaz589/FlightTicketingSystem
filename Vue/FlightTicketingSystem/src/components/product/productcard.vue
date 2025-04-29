<template>
    <v-card>
            <v-img
              :src="'http://localhost:8080' + product.image"
              height="200px"
              cover
            ></v-img>
  
            <v-card-title> {{ product.name }}</v-card-title>
            <v-card-subtitle>{{ product.needmiles }}哩</v-card-subtitle>
  
            <v-card-actions>
              <v-btn color="primary" block @click="cartstore.addProduct(product)">加入購物車</v-btn>
            </v-card-actions>
          </v-card>
</template>

<script setup>
import { usecartStore } from '@/stores/usecartStore'; 
defineProps(["product"])

const cartstore = usecartStore();

</script>

<style  scoped>

</style>