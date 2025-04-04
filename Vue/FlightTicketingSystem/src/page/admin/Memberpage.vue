<template>
    <div>
<h1>這是會員頁面</h1>
    </div>
    <v-container>
        <v-autocomplete 
        label="搜尋會員ID"
        v-model="targetId.id">
        </v-autocomplete>

        <v-btn prepend-icon="mdi mdi-magnify" @click="search"> 搜尋全部會員 </v-btn>

        

    </v-container>
    {{targetId}}
    <!-- {{targetId.fullName}} -->
</template>

<script setup>
import { ref } from 'vue';
import { ApiMember } from '@/utils/API';

// 設定空ref接查詢結果
const findMember = ref([]);
const allCities = ref(1);
//搜尋用id
const targetId = ref({
    id: ""
})

// ApiMember.getMember(1).then((res)=>{
//     targetId.value = res.data;
// })

function search(){

    ApiMember.getAllMember().then((res)=>{
    targetId.value = res.data;
    console.log(1);   
    })
}








</script>

<style  scoped>

</style>
  
  