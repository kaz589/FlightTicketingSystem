<template>
  <div v-if="modalVisible" class="modal-overlay">
    <div class="modal">
      <h2>{{ isEdit ? "修改行程" : "新增行程" }}</h2>

      <div class="form-group">
        <label>選擇城市</label>
        <select v-model="selectedCityId" @change="loadAttractions">
          <option disabled value="">請選擇</option>
          <option v-for="city in cities" :key="city.id" :value="city.id">
            {{ city.name }}
          </option>
        </select>
      </div>

      <div v-if="attractions.length" class="form-group">
        <label>選擇景點</label>
        <select v-model="selectedAttraction">
          <option disabled value="">請選擇</option>
          <option v-for="spot in attractions" :key="spot.id" :value="spot.name">
            {{ spot.name }}
          </option>
        </select>
      </div>

      <div class="form-actions">
        <button @click="confirmEvent">
          {{ isEdit ? "保存修改" : "新增" }}
        </button>
        <button v-if="isEdit" class="danger" @click="deleteEvent">刪除</button>
        <button @click="closeModal">取消</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useCityStore } from "@/stores/cityStore";
import { useAttractionStore } from "@/stores/attractionStore";

const props = defineProps({
  modalVisible: Boolean,
  isEdit: Boolean,
  eventData: Object,
  startDate: String,
  endDate: String,
});

const emit = defineEmits(["confirm", "delete", "cancel"]);

const cityStore = useCityStore();
const attractionStore = useAttractionStore();

const selectedCityId = ref(props.eventData?.cityId || "");
const selectedAttraction = ref(props.eventData?.title || "");

const cities = computed(() => cityStore.cities);
const attractions = ref([]);

const loadAttractions = async () => {
  if (selectedCityId.value) {
    attractions.value = await attractionStore.getAttractionsByCity(
      selectedCityId.value
    );
  }
};

const confirmEvent = () => {
  if (!selectedCityId.value || !selectedAttraction.value) return;
  emit("confirm", {
    title: selectedAttraction.value,
    start: props.startDate,
    end: props.endDate,
    cityId: selectedCityId.value,
  });
  closeModal();
};

const deleteEvent = () => {
  emit("delete", props.eventData);
  closeModal();
};

const closeModal = () => {
  emit("cancel");
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}
.modal {
  background: white;
  padding: 2em;
  border-radius: 8px;
  width: 400px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}
.form-group {
  margin-bottom: 1em;
}
.form-actions {
  display: flex;
  justify-content: space-between;
  gap: 10px;
}
.danger {
  background-color: #dc2626;
  color: white;
}
</style>
