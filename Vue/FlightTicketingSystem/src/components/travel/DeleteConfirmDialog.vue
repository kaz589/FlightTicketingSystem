<template>
  <v-dialog
    v-model="model"
    max-width="420"
    transition="dialog-bottom-transition">
    <v-card class="rounded-2xl pa-6" color="background">
      <v-card-title class="text-center text-2xl font-bold mb-2">
        <v-icon size="48" color="error" class="mb-2">mdi-alert-circle</v-icon>
        <div>{{ title }}</div>
      </v-card-title>
      <v-card-text class="text-center text-gray-500 text-base">
        {{ message }}
      </v-card-text>
      <v-card-actions class="justify-center mt-6">
        <v-btn
          variant="flat"
          color="error"
          class="rounded-full px-6 ml-4"
          @click="$emit('confirm')">
          確定刪除
        </v-btn>
        <v-btn
          variant="outlined"
          color="grey"
          class="rounded-full px-6"
          @click="$emit('cancel')">
          取消
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
const props = defineProps({
  modelValue: Boolean,
  title: { type: String, default: "確認刪除" },
  message: { type: String, default: "此操作無法復原，是否確定刪除？" },
});
const emit = defineEmits(["update:modelValue", "confirm", "cancel"]);

const model = defineModel(); // 自動綁定 v-model
</script>
