<template>
  <div>
    <v-dialog v-model="internalDialog" max-width="600" persistent>
      <v-card>
        <v-card-title class="text-h6 d-flex justify-space-between align-center">
          <slot name="title">Dialog Title</slot>
          <v-btn icon @click="close">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-card-title>

        <v-card-text>
          <slot />
        </v-card-text>

        <v-card-actions class="justify-end">
          <slot name="actions">
            <v-btn text @click="close">取消</v-btn>
            <v-btn color="primary" @click="confirm">確定</v-btn>
          </slot>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup>
import { watch, ref } from "vue";

const props = defineProps({
  modelValue: Boolean,
});

const emit = defineEmits(["update:modelValue", "confirm", "cancel"]);

const internalDialog = ref(props.modelValue);

watch(
  () => props.modelValue,
  (val) => {
    internalDialog.value = val;
  }
);

watch(internalDialog, (val) => {
  emit("update:modelValue", val);
});

const close = () => {
  emit("cancel");
  internalDialog.value = false;
};

const confirm = () => {
  emit("confirm");
  internalDialog.value = false;
};
</script>

<style scoped></style>
