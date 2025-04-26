<template>
 
 <v-card
    class="d-flex align-center pa-4"
    elevation="5"
    rounded="xl"
  >
    <v-img
      :src="'http://localhost:8080' + product.image"
      height="100"
      width="100"
      class="mr-4 rounded"
      cover
    ></v-img>

    <v-card-text class="flex-grow-1">
      <div class="text-h6 font-weight-medium">{{ product.name }}</div>
      <div class="text-body-1 text-grey-darken-1">里程：{{ product.needmiles }}</div>
    </v-card-text>
            <v-number-input v-model="product.quantity"
          control-variant="stacked"
          inset
          :min="1"
        ></v-number-input>
            <v-card-actions>
              <v-btn color="primary" block @click="cartstore.removeProduct(product.id)">刪除商品</v-btn>
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