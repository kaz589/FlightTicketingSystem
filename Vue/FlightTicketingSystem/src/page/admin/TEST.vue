<template>
  <v-btn color="primary" @click="saveAirport(record)">保存</v-btn>
{{  }}
</template>

<script setup>
  import { onMounted, ref, shallowRef } from 'vue'
  import { useDate } from 'vuetify'
  import axios from 'axios'
  const record = {
    iataCode: "RAX",
    airportName: "Test Airport",
    countryRegion: "Test Country",
    city: "Test City",
    
}

function saveAirport(record) {
  axios
    .post("http://localhost:8080/api/airports", record)
    .then((response) => {
      console.log("新增成功！", response.data);
      alert("新增成功！");
    })
    .catch((error) => {
      console.error("新增失敗：", error.response ? error.response.data : error);
      alert("新增失敗！");
    });
}

 
</script>
