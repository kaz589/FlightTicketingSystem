<template>
  <v-dialog v-model="model" max-width="600">
    <v-card>
      <v-card-title class="text-h6 font-weight-bold">新增城市</v-card-title>
      <v-card-text>
        <v-form ref="formRef" v-model="valid" lazy-validation>
          <v-img
            v-if="previewUrl"
            :src="previewUrl"
            height="150"
            class="mt-2 rounded"
            cover />
          <v-text-field v-model="form.name" label="名稱" :rules="[required]" />
          <v-text-field
            v-model="form.country"
            label="國家"
            :rules="[required]" />
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
            label="城市圖片"
            accept="image/*"
            prepend-icon=""
            prepend-inner-icon="mdi-camera"
            v-model="form.imageFile"
            show-size
            @change="previewImage" />
        </v-form>
      </v-card-text>

      <v-card-actions class="justify-end">
        <v-btn
          :disabled="!valid || loading"
          variant="outlined"
          color="primary"
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
import { ref, reactive, watch } from "vue";
import axios from "axios";

const props = defineProps({
  modelValue: Boolean,
});
const emit = defineEmits(["update:modelValue", "created"]);

const model = ref(props.modelValue);
watch(
  () => props.modelValue,
  (val) => (model.value = val)
);
watch(model, (val) => emit("update:modelValue", val));

const valid = ref(false);
const loading = ref(false);
const previewUrl = ref("");
const formRef = ref(null);

const form = reactive({
  name: "",
  country: "",
  latitude: null,
  longitude: null,
  imageFile: null,
});

const required = (v) => !!v || "必填";
const number = (v) => (typeof v === "number" && !isNaN(v)) || "必須是數字";

const previewImage = () => {
  const file = form.imageFile;
  if (file) {
    previewUrl.value = URL.createObjectURL(file);
  }
};

const resetForm = () => {
  form.name = "";
  form.country = "";
  form.latitude = null;
  form.longitude = null;
  form.imageFile = null;
  previewUrl.value = "";
  formRef.value?.resetValidation();
};

const close = () => {
  model.value = false;
  resetForm();
};

const submit = async () => {
  const isValid = await formRef.value?.validate();
  if (!isValid) return;

  loading.value = true;
  try {
    let imageUrl = null;

    if (form.imageFile) {
      const formData = new FormData();
      formData.append("image", form.imageFile);
      const uploadRes = await axios.post(
        "http://localhost:8080/cities/upload",
        formData,
        { headers: { "Content-Type": "multipart/form-data" } }
      );
      imageUrl = uploadRes.data.url;
    }

    await axios.post("http://localhost:8080/cities", {
      name: form.name,
      country: form.country,
      latitude: form.latitude,
      longitude: form.longitude,
      ...(imageUrl && { image: imageUrl }),
    });

    emit("created");
    close();
  } catch (err) {
    console.error("新增城市失敗", err);
  } finally {
    loading.value = false;
  }
};
</script>
