<template>
  <div class="flex justify-center mt-6">
    <div
      class="flex w-full max-w-xl shadow-md rounded-full border border-gray-200 overflow-hidden"
    >
      <input
        v-model="localQuery"
        type="text"
        placeholder="請輸入關鍵字"
        class="w-full px-6 py-3 focus:outline-none"
      />
      <button
        class="bg-emerald-400 text-white px-6 py-3 font-semibold hover:bg-emerald-500"
        @click="emitSearch"
      >
        Search
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";

const props = defineProps({
  modelValue: String,
});
const emit = defineEmits(["update:modelValue", "search"]);

const localQuery = ref(props.modelValue || "");

watch(localQuery, (val) => {
  emit("update:modelValue", val);
});

const emitSearch = () => {
  emit("search");
};
</script>
