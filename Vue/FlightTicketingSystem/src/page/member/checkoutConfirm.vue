<template>
    <v-container class="py-8">
      <v-card class="pa-6" elevation="6" rounded="xl">
        <v-card-title class="text-h5 mb-4">結帳成功！請確認結帳資訊</v-card-title>
  
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field
            v-model="formData.address"
            label="收件地址"
            placeholder="請輸入完整收件地址"
            :rules="[rules.required]"
            prepend-inner-icon="mdi-home"
          ></v-text-field>
  
          <v-text-field
            v-model="formData.email"
            label="電子郵件"
            placeholder="example@example.com"
            :rules="[rules.required, rules.email]"
            prepend-inner-icon="mdi-email"
          ></v-text-field>
  
          <v-text-field
            v-model="formData.phone"
            label="手機號碼"
            placeholder="09xxxxxxxx"
            :rules="[rules.required, rules.phone]"
            prepend-inner-icon="mdi-cellphone"
          ></v-text-field>
  
          <v-btn
            class="mt-6"
            color="primary"
            block
            @click="submitOrder"
            :disabled="!valid"
          >
            確認送出
          </v-btn>
        </v-form>
      </v-card>
    </v-container>
  </template>
  
  <script setup>
  import { ApiMember } from '@/utils/API';
import { ref } from 'vue';
  
  const valid = ref(false);
  
  const formData = ref({
    address: '',
    email: '',
    phone: '',
  });
  
  const rules = {
    required: (v) => !!v || '此欄位必填',
    email: (v) =>
      /.+@.+\..+/.test(v) || '請輸入正確的電子郵件格式',
    phone: (v) =>
      /^09\d{8}$/.test(v) || '請輸入正確的手機號碼格式',
  };
  
  function submitOrder() {
    if (valid.value) {
      console.log('送出資料:', formData.value);
      alert('結帳成功！');
        // 這邊可以加 API 請求送出訂單資料
        ApiMember.updateMember
        //成功後寄信

      //跳轉回里程兌換首頁
    }
  }
  </script>
  
  <style scoped>
  </style>
  