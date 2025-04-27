<template>
    
<!-- 商品類別菜單 -->
 <!-- 商品里程菜單 -->


    <!-- 商品顯示 一排四個卡片-->
    <v-container>
        <br/>
     <br/>
    <!-- 商品名稱搜尋欄 -->
    <v-text-field
    
      append-icon="mdi-magnify"
      label="輸入商品名稱"
      single-line
      hide-details
      class="mx-4"
      ></v-text-field>
    <br/>
    <br/>
      <v-row>
        <v-col
          v-for="n in 12"
          :key="n"
          cols="12"
          md="3"
        >
          <v-card>
            <v-img
              src="https://picsum.photos/300/200"
              height="200px"
              cover
            ></v-img>
  
            <v-card-title>商品 {{ n }}</v-card-title>
            <v-card-subtitle>超值特價！</v-card-subtitle>
  
            <v-card-actions>
              <v-btn color="primary" block>加入購物車</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
<script setup>





</script>

<style  scoped>

</style>