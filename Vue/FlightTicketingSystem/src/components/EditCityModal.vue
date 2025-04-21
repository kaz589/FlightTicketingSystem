<script setup>
import Input from "@/components/Input.vue";
import { ref } from "vue";

const form = ref({
  name: "",
  latitude: "",
});

const errors = ref({
  name: "",
  latitude: "",
});

const validate = () => {
  errors.value.name = form.value.name ? "" : "城市名稱為必填";
  errors.value.latitude = form.value.latitude ? "" : "經度為必填";
};
</script>

<template>
  <Input label="城市名稱" v-model="form.name" :error="errors.name" required />
  <Input
    label="經度"
    type="number"
    v-model="form.latitude"
    :error="errors.latitude"
    required
  />

  <button
    @click="validate"
    class="mt-4 bg-blue-600 text-white px-4 py-2 rounded"
  >
    送出
  </button>
</template>
