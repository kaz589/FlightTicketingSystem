<template>
  <div class="d-flex justify-center mt-6">
    <div class="custom-search-wrapper">
      <v-icon class="search-icon" icon="mdi-magnify" />
      <input
        v-model="query"
        type="text"
        placeholder="請輸入關鍵字"
        class="search-input"
        @keyup.enter="emitSearch"
        :disabled="loading"
      />
      <button class="search-btn" @click="emitSearch" :disabled="loading">
        <template v-if="loading">
          <v-progress-circular
            indeterminate
            size="20"
            width="2"
            color="white"
          />
        </template>
        <template v-else> 搜尋 </template>
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { VIcon, VProgressCircular } from "vuetify/components";

const query = defineModel();
const emit = defineEmits(["search"]);
const loading = ref(false);
const emitSearch = async () => {
  if (!query.value.trim()) return;

  loading.value = true;
  emit("search", () => {
    loading.value = false;
  });
};
</script>

<style scoped>
.custom-search-wrapper {
  display: flex;
  align-items: center;
  background-color: white;
  border-radius: 9999px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
  padding: 0 8px 0 16px;
  height: 56px;
  width: 100%;
  max-width: 720px;
}

.search-icon {
  font-size: 24px;
  color: #333;
  margin-right: 8px;
}

.search-input {
  flex: 1;
  border: none;
  outline: none;
  font-size: 16px;
  padding: 0;
  height: 100%;
  color: #333;
  background-color: transparent;
}

.search-input::placeholder {
  color: #888;
}

.search-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #1976d2;
  color: #ffff;
  font-weight: 600;
  border: none;
  border-radius: 9999px;
  padding: 0 24px;
  height: 40px;
  cursor: pointer;
  transition: background-color 0.2s ease;
  margin-left: 8px;
  min-width: 80px;
}

.search-btn:hover {
  background-color: #2fd2a0;
}

.search-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>
