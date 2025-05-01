<template>
  <v-dialog v-model="model" max-width="700">
    <v-card>
      <v-card-title class="text-h6 font-weight-bold">新增景點</v-card-title>
      <v-card-text>
        <v-form ref="formRef" v-model="valid" lazy-validation>
          <v-carousel
            v-if="photos.length > 0"
            height="180"
            hide-delimiter-background
            delimiter-icon="mdi-circle"
            show-arrows="hover"
            cycle
            class="rounded-t-xl mt-4">
            <v-carousel-item v-for="(photo, i) in photos" :key="i">
              <v-img :src="photo.url" height="180" cover />
            </v-carousel-item>
          </v-carousel>

          <v-text-field v-model="form.name" label="名稱" :rules="[required]" />
          <v-select
            v-model="form.cityName"
            :items="localCities.map((c) => c.name)"
            label="所屬城市"
            :rules="[required]"
            :loading="cityLoading"
            no-data-text="目前沒有可選城市" />
          <v-text-field
            v-model="form.address"
            label="地址"
            :rules="[required]" />
          <v-textarea v-model="form.description" label="描述" />

          <v-text-field
            v-model.number="form.rating"
            label="評分"
            type="number"
            :rules="[required, ratingRule]" />
          <v-combobox
            v-model="form.category"
            label="分類"
            multiple
            chips
            deletable-chips
            hint="可多選"
            persistent-hint />
          <v-text-field
            v-model.number="form.latitude"
            label="緯度"
            type="number"
            :rules="[required, number]" />
          <v-text-field
            v-model.number="form.longitude"
            label="經度"
            type="number"
            :rules="[required, number]" />

          <v-file-input
            v-model="form.imageFiles"
            label="圖片（可多選）"
            accept="image/*"
            prepend-icon=""
            prepend-inner-icon="mdi-camera"
            multiple
            show-size
            @change="previewImages" />
        </v-form>
      </v-card-text>
      <v-card-actions class="justify-end">
        <v-btn
          :disabled="!valid || loading"
          color="primary"
          variant="outlined"
          @click="submit">
          <v-progress-circular
            v-if="loading"
            indeterminate
            size="18"
            width="2"
            color="white"
            class="mr-2" />
          確認
        </v-btn>
        <v-btn text variant="outlined" @click="close">取消</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, reactive, watch, onMounted } from "vue";
import axios from "axios";
import { useCityStore } from "@/stores/cityStore";

const props = defineProps({
  modelValue: Boolean,
});
const emit = defineEmits(["update:modelValue", "created"]);

const model = ref(props.modelValue);
const valid = ref(false);
const loading = ref(false);
const photos = ref([]);
const formRef = ref(null);

const cityStore = useCityStore();
const localCities = ref([]);
const cityLoading = ref(false);

const form = reactive({
  name: "",
  cityName: "",
  address: "",
  description: "",
  rating: 0,
  category: [],
  latitude: null,
  longitude: null,
  imageFiles: [],
});

watch(
  () => props.modelValue,
  async (val) => {
    model.value = val;
    if (val) {
      await loadCities();
      resetForm();
    }
  }
);

const required = (v) => !!v || "必填";
const number = (v) => (typeof v === "number" && !isNaN(v)) || "必須是數字";
const ratingRule = (v) => (v >= 0 && v <= 5) || "評分必須在 0 ~ 5";

async function loadCities() {
  try {
    cityLoading.value = true;
    await cityStore.fetchCities();
    localCities.value = cityStore.cities;
  } catch (err) {
    console.error("載入城市失敗", err);
  } finally {
    cityLoading.value = false;
  }
}

function previewImages() {
  const files = Array.isArray(form.imageFiles) ? form.imageFiles : [];
  photos.value = files.map((f) => ({
    url: URL.createObjectURL(f),
    name: f.name,
  }));
}

function resetForm() {
  form.name = "";
  form.cityName = "";
  form.address = "";
  form.description = "";
  form.rating = 0;
  form.category = [];
  form.latitude = null;
  form.longitude = null;
  form.imageFiles = [];
  photos.value = [];
  valid.value = false;
}

function close() {
  model.value = false;
}

async function submit() {
  const isValid = await formRef.value?.validate();
  if (!isValid) return;

  loading.value = true;
  try {
    const selectedCity = localCities.value.find(
      (city) => city.name === form.cityName
    );
    if (!selectedCity) throw new Error("未找到對應城市");

    const res = await axios.post("http://localhost:8080/attractions", {
      name: form.name,
      address: form.address,
      cityId: selectedCity.id,
      description: form.description,
      rating: form.rating,
      category: form.category,
      latitude: form.latitude,
      longitude: form.longitude,
    });

    const attractionId = res.data.id;

    if (form.imageFiles.length > 0) {
      for (const file of form.imageFiles) {
        const formData = new FormData();
        formData.append("image", file);
        const uploadRes = await axios.post(
          "http://localhost:8080/photos/upload",
          formData,
          { headers: { "Content-Type": "multipart/form-data" } }
        );
        await axios.post("http://localhost:8080/photos", {
          attractionId,
          url: uploadRes.data.url,
          caption: form.name,
        });
      }
    }

    emit("created");
    close();
  } catch (err) {
    console.error("新增景點失敗", err);
  } finally {
    loading.value = false;
  }
}
</script>
