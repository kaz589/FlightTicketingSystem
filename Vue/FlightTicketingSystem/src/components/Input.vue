<!-- components/Input.vue -->
<template>
  <div class="flex items-center gap-2">
    <label :for="id" class="w-24 text-sm font-medium">{{ label }}</label>
    <input
      :id="id"
      :type="type"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
    />
  </div>
</template>

<script setup>
defineProps({
  id: String,
  label: String,
  modelValue: [String, Number],
  type: {
    type: String,
    default: "text",
  },
});
defineEmits(["update:modelValue"]);
</script>
