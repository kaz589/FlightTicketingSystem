<template>
  <div></div>
</template>

<script setup>
import { testapi } from "./utils/API";

testapi()
      .then((res) => {
        console.log(res);
      })
      .catch();
</script>

<style></style>
